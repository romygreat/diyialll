package com.gddiyi.aom.presenter;

import android.util.Log;
import android.util.SparseArray;

import com.gddiyi.aom.model.PlayData;
import com.gddiyi.aom.model.VideoPlayAll;
import com.gddiyi.aom.model.dto.ResponseJsonVideo;
import com.gddiyi.aom.netutils.DownLoadVideoUtils;
import com.gddiyi.aom.netutils.DownloadUtil;

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
    public VideoPresenter() {

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
         PlayData playData=new PlayData(netPath,videoName);
         playData.setLocalPath("sdcard/ad/"+videoName);
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
}
