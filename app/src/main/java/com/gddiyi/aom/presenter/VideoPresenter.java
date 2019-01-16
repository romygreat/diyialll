package com.gddiyi.aom.presenter;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.util.SparseArray;

import com.gddiyi.aom.constant.VSConstances;
import com.gddiyi.aom.model.PlayData;
import com.gddiyi.aom.model.VideoPlayAll;
import com.gddiyi.aom.model.dto.ResponseJsonVideo;
import com.gddiyi.aom.netutils.DownLoadVideoUtils;
import com.gddiyi.aom.netutils.DownloadUtil;
import com.gddiyi.interfacemanager.VideoInterface;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import lombok.Getter;
import lombok.Setter;

import static com.gddiyi.aom.model.VideoPlayAll.getInstance;

/**
 * @author romy
 * @time 2018/1/12
 * 保存与操作下载网络视频的类
 */
public class VideoPresenter {
    @Getter
    int count;
    String doMain;
    String path;
    String videoName;
    String TAG = "VideoTAG";
    //该类很重要，通过该类的访问得到视频路径，本地路径等信息
    VideoPlayAll<PlayData> sparseArray;
    @Setter
    @Getter
    VideoInterface videoInterface;


    public void setDownloadVideReady(DownloadVideoReady downloadVideReady) {
        this.downloadVideReady = downloadVideReady;
    }

    int downLoadVideoCount;

    public DownloadVideoReady getDownloadVideReady() {
        return downloadVideReady;
    }

    DownloadVideoReady downloadVideReady;

    public void setDoMain(String doMain) {
        this.doMain = doMain;
    }

    public String getDoMain() {
        return doMain;
    }

    public VideoPresenter() {

    }

    //将在请求视频回来的数据保存到VideoPlayAll类中，方便管理
    public VideoPlayAll<PlayData> saveVideoPrsenter(ResponseJsonVideo.DataBean dataBeans) {
        count = dataBeans.getCount();
        Log.i("Response", "saveVideoPrsenter: count" + count);
        List<ResponseJsonVideo.DataBean.ListBean> listBean = dataBeans.getList();
        sparseArray = getInstance();
        Log.i("dataBenscount", "saveVideoPrsenter: " + dataBeans.getCount());
        sparseArray.setCount(count);
        for (int i = 0; i < dataBeans.getCount(); i++) {
            String netPath;
            path = listBean.get(i).getPath();
            netPath = "http://" + getDoMain() + "/" + path;
            videoName = path.substring(3);
//         PlayData playData=new PlayData(netPath,videoName);
            PlayData playData = new PlayData(videoName, netPath);
            playData.setLocalPath(videoName);
            Log.i("localPath", "saveVideoPrsenter: " + playData.getLocalPath());
            sparseArray.getSaveData().put(i, playData);
            Log.i("savei", "saveVideoPrsenter: " + i);
        }
        printSavedata(sparseArray);
        if (downloadVideReady != null) {
            downloadVideReady.noticefyDownLoadReady(sparseArray);
        }

        return sparseArray;
    }

    void printSavedata(VideoPlayAll<PlayData> dataSparseArray) {
        for (int i = 0; i < dataSparseArray.getCount(); i++) {
            Log.i("response", "printSavedata: " + dataSparseArray.getSaveData().get(i).getLocalPath());
        }
    }

    //所有网络地址
    public void DownloadAllNetVideo() {
        String[] netVideoPath = VideoPlayAll.getInstance().getAllNetvideoPath();
        DownloadUtil downloadUtil = DownloadUtil.get();
        for (int i = 0; i < netVideoPath.length; i++) {
            downloadUtil.download(netVideoPath[i], "ad", new DownloadUtil.OnDownloadListener() {
                @Override
                public void onDownloadSuccess() {
                    downLoadVideoCount++;
                    Log.i("DownLoadVideo", "onDownloadSuccess: ");
                    if (count == downLoadVideoCount) {
                        Log.i("DownLoadVideo", "AllOnDownloadSuccess: ");
                    }
                }

                @Override
                public void onDownloading(int progress) {

                }

                @Override
                public void onDownloadFailed() {

                }
            });
        }

    }

    //通知可以正式下载视频接口
    public interface DownloadVideoReady {
        void noticefyDownLoadReady(VideoPlayAll<PlayData> sparseArray);

        void noticefyUpdate(VideoPlayAll<PlayData> sparseArray,  ArrayList list);
    }

    public File createFile(String fileName) {
        File jsonFile = new File(fileName);
        if (!jsonFile.exists()) {
            try {
                jsonFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return jsonFile;
    }

    //以json的形式写入本地文件，并没有使用数据库存储方式
    public JsonObject save2LocalFile(VideoPlayAll<PlayData> sparseArray) {
        JsonObject jsonObject = new JsonObject();
        JsonArray jsonArrayLocalPath = new JsonArray();
        JsonArray jsonArrayNetPath = new JsonArray();
//        jsonArray.add();
        Log.i("test", "save2LocalFile: " + sparseArray.getCount());
        for (int i = 0; i < sparseArray.getCount(); i++) {
            jsonArrayLocalPath.add(sparseArray.get(i).getLocalPath());
            jsonArrayNetPath.add(sparseArray.get(i).getNetVideoPath());
            Log.i("testi", "save2LocalFile: " + i);
        }
        jsonObject.add("localPath", jsonArrayLocalPath);
        jsonObject.add("netPath", jsonArrayNetPath);
        Log.i("jsonObject", "save2LocalFile: " + jsonObject.toString());
        //创建本地json文件
        FileIoPrensenter fileIoPrensenter = new FileIoPrensenter();
        fileIoPrensenter.writeFile(VSConstances.JSONFILEPATH, jsonObject.toString());

        return jsonObject;
    }

    //读取本地文件
    public String readJsonFile() {
        FileIoPrensenter fileIoPrensenter = new FileIoPrensenter();
        String stringjson = fileIoPrensenter.ReadFile(VSConstances.JSONFILEPATH);
        Log.i("readJsonFile123", "readJsonFile: ");
        return stringjson;
    }

    //是否更新检查，如果有同时通知更新
    public boolean checkUpdate() {
        String localName = readJsonFile();
        Log.i("test123", "checkUpdate: " + localName);
        String[] tmpPathName = null;
        org.json.JSONObject jsonObject1 = null;
        String[] notsavePathAName = null;
        try {
            jsonObject1 = new JSONObject(localName);
            org.json.JSONArray localPathArray1 = (org.json.JSONArray) jsonObject1.get(VSConstances.LOCALPATH);
            tmpPathName = new String[localPathArray1.length()];

            notsavePathAName = sparseArray.getAllLocalVideoPath();
//            org.json.JSONArray localPathArray1 = (org.json.JSONArray) jsonObject1.get("localPath");
            tmpPathName = new String[localPathArray1.length()];
            for (int i = 0; i < localPathArray1.length(); i++) {
                tmpPathName[i] = (String) localPathArray1.get(i);
                Log.i(TAG, "checkUpdate: " + tmpPathName[i]);
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.i("e.print", "checkUpdate: " + e.toString());
        }
        boolean isCheckUpdate = Arrays.equals(tmpPathName, notsavePathAName);
        //无更新时走if,有更新走else
//        if (isCheckUpdate)return isCheckUpdate;
////        else
        {

            try {
                    ArrayList<Integer> arrayList=new ArrayList<>();
                String[] fileNames = getALlSDadVideoNames();
                for (int i = 0; i < notsavePathAName.length; i++) {
                    boolean isNeedtoUPdate = true;
                    for (int j = 0; j < fileNames.length; j++) {
                        if (notsavePathAName[i].equals(fileNames[j])) {
                            isNeedtoUPdate= false;
                        }
                    }
                    if (isNeedtoUPdate){
                        arrayList.add(i);
                    }
                }
                //int[] updateNum= arrayList.toArray();
                downloadVideReady.noticefyUpdate(sparseArray, arrayList);
            } catch (Exception e) {
                save2LocalFile(sparseArray);
                //updateVideoMessage = new int[50];
                Log.i(TAG, "checkUpdate: Exception" + e.toString());
            }
        }
        Log.i("checkString", "checkUpdate: isCheckUpdate" + isCheckUpdate);
        return isCheckUpdate;
    }

    public String[] getALlSDadVideoNames() {
        File fileDir = new File(VSConstances.SDdir);
        String[] fileNames = null;
        if (fileDir.exists() && fileDir.isDirectory()) {
            File[] files = fileDir.listFiles();
            fileNames = new String[files.length];
            for (int i = 0; i < files.length; i++) {
                fileNames[i] = files[i].getName();
            }
        }
        return fileNames;
    }

    public void deleteSDadVideo() {
        String localName = readJsonFile();
        Log.i(" deleteSDVideo", "localName123: " + localName);
        String[] tmpPathName = getALlSDadVideoNames();
        org.json.JSONObject jsonObject1 = null;
        int queryCount = 0;
        try {
            jsonObject1 = new JSONObject(localName);
            org.json.JSONArray localjsonFile = (org.json.JSONArray) jsonObject1.get(VSConstances.LOCALPATH);
            Log.i(TAG, "deleteSDVideo:tmpPathName " + tmpPathName.length);
            for (int i = 0; i < tmpPathName.length; i++) {
                Log.i(TAG, "deleteSDVideo: " + i);
                boolean isNeedDeleteVideo = true;
                for (int j = 0; j < localjsonFile.length(); j++) {
                    if (tmpPathName[i].equals(localjsonFile.get(j))) {
                        isNeedDeleteVideo = false;
                    }
                }
                if (isNeedDeleteVideo) {
                    String deleteVideoName = tmpPathName[i];
                    String fileName = "sdcard/ad/" + deleteVideoName;
                    Log.i(TAG, "deleteSDVideo: Name" + fileName);
                    File file = new File(fileName);
                    file.delete();
                    isNeedDeleteVideo = true;
                }
                Log.i(TAG, "deleteSDVideo: " + isNeedDeleteVideo);
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.i("e.print", "checkUpdate: " + e.toString());
        }
    }

}
