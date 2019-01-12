package com.gddiyi.aom.presenter;

import android.util.Log;
import android.util.SparseArray;

import com.gddiyi.aom.model.PlayData;
import com.gddiyi.aom.model.VideoPlayAll;
import com.gddiyi.aom.model.dto.ResponseJsonVideo;

import java.util.List;

public class VideoPresenter {
    int count;

    String doMain;
    String path;
    String videoName;
//    SparseArray<PlayData> saveData;
    VideoPlayAll<PlayData> sparseArray;
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
     sparseArray=new VideoPlayAll<>(count);
     for (int i=0;i<dataBeans.getCount()-1;i++){
         String netPath;
         path=listBean.get(i).getPath();
         netPath="http://"+getDoMain()+"/"+path;
         videoName=path.substring(3);
         Log.i("Response:", "saveVideoPrsenter: videoName=="+videoName);
         Log.i("Response:", "saveVideoPrsenter: netpath=="+netPath);
         PlayData playData=new PlayData(netPath,videoName);
         playData.setLocalPath("sdcard/ad/"+videoName);
         Log.i("Response:", "saveVideoPrsenter: localpath=="+playData.getLocalPath());
         sparseArray.getSaveData().put(i,playData);
     }
        
     printSavedata(sparseArray);
        return sparseArray;
    }
    void printSavedata(VideoPlayAll<PlayData> dataSparseArray){
          for (int i=0;i<dataSparseArray.getCount()-1;i++){
              Log.i("response", "printSavedata: "+dataSparseArray.getSaveData().get(i).getLocalPath());
          }

    }
}
