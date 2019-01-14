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
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static com.gddiyi.aom.model.VideoPlayAll.getInstance;

/**
 *
 * @author romy
 * @time 2018/1/12
 * 保存与操作下载网络视频的类
 *
 */
public class VideoPresenter {
    int count;
    String doMain;
    String path;
    String videoName;
    Context mContext;
    //该类很重要，通过该类的访问得到视频路径，本地路径等信息
    VideoPlayAll<PlayData> sparseArray;



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
    public VideoPresenter(Context context) {
        mContext=context;


    }
    //将在请求视频回来的数据保存到VideoPlayAll类中，方便管理
    public  VideoPlayAll<PlayData> saveVideoPrsenter(ResponseJsonVideo.DataBean dataBeans){
     count=dataBeans.getCount();
     Log.i("Response", "saveVideoPrsenter: count"+count);
     List< ResponseJsonVideo.DataBean.ListBean> listBean=dataBeans.getList();
     sparseArray=getInstance();
     sparseArray.setCount(count);
     for (int i=0;i<dataBeans.getCount()-1;i++){
         String netPath;
         path=listBean.get(i).getPath();
         netPath="http://"+getDoMain()+"/"+path;
         videoName=path.substring(3);
//         PlayData playData=new PlayData(netPath,videoName);
         PlayData playData=new PlayData(videoName,netPath);
         playData.setLocalPath(videoName);
         Log.i("localPath", "saveVideoPrsenter: "+playData.getLocalPath());
         sparseArray.getSaveData().put(i,playData);

     }
        Log.i("response", "saveVideoPrsenter: "+sparseArray.getLocalVideoPath(3));
       printSavedata(sparseArray);
       if (downloadVideReady!=null){
           downloadVideReady.noticefyDownLoadReady(sparseArray);
       }

        return sparseArray;
    }
    void printSavedata(VideoPlayAll<PlayData> dataSparseArray){
          for (int i=0;i<dataSparseArray.getCount()-1;i++){
              Log.i("response", "printSavedata: "+dataSparseArray.getSaveData().get(i).getLocalPath());
          }
    }
    public void DownloadAllNetVideo(){
      String[] netVideoPath= VideoPlayAll.getInstance().getAllNetvideoPath();
      DownloadUtil downloadUtil=DownloadUtil.get();
      for (int i=0;i<netVideoPath.length-1;i++){
          downloadUtil.download(netVideoPath[i], "ad", new DownloadUtil.OnDownloadListener() {
              @Override
              public void onDownloadSuccess() {
                  downLoadVideoCount++;
                  Log.i("DownLoadVideo", "onDownloadSuccess: ");
                  if (count==downLoadVideoCount){
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
   public  interface  DownloadVideoReady{
      void  noticefyDownLoadReady(VideoPlayAll<PlayData> sparseArray);
    }
    public File createFile(String fileName){
        File jsonFile=new File(fileName);
        if(!jsonFile.exists()){
            try {
                jsonFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return jsonFile;
    }
    public JsonObject save2LocalFile(VideoPlayAll<PlayData> sparseArray){

        JsonObject jsonObject=new JsonObject();
        JsonArray jsonArrayLocalPath=new JsonArray();
        JsonArray jsonArrayNetPath=new JsonArray();
//        jsonArray.add();
        for (int i=0;i<sparseArray.getCount()-1;i++){
            jsonArrayLocalPath.add(sparseArray.get(i).getLocalPath());
            jsonArrayNetPath.add(sparseArray.get(i).getNetVideoPath());
        }
        jsonObject.add("localPath",jsonArrayLocalPath);
        jsonObject.add("netPath",jsonArrayNetPath);
        Log.i("jsonObject", "save2LocalFile: "+jsonObject.toString());
        FileIoPrensenter fileIoPrensenter=new FileIoPrensenter();
            fileIoPrensenter.writeFile(VSConstances.JSONFILEPATH,jsonObject.toString());

        return jsonObject;
    }
    public String readJsonFile(){
        Log.i("readjsonObject", "readJsonFile: ");
        FileIoPrensenter fileIoPrensenter=new FileIoPrensenter();
        String stringjson= fileIoPrensenter.ReadFile(VSConstances.JSONFILEPATH);
        Log.i("readJsonFile123", "readJsonFile: ");
        return stringjson;
    }

}
