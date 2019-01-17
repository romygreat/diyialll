package com.gddiyi.aom.diyiproject;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() {
        assertEquals(4, 2 + 2);
    }
}
//updateVideoMessage = new int[getALlSDadVideoNames().length];
//
//        int updateVideoMessageIndex = 0;
//        for (int i = 0; i < notsavePathAName.length; i++) {
//        int countNumUpdate = 0;
//        //播放顺序1中，在本地所有文件不同的次数
//        for (int j = 0; j < tmpPathName.length; j++) {
//        if (!(notsavePathAName[i].equals(tmpPathName[j]))) {
//        countNumUpdate++;
//        }
//        }
//        //在这需要更新视频文件
//        //本地没有相应的视频文件，需要下载
//        if (countNumUpdate >= tmpPathName.length) {
//        updateVideoMessage[updateVideoMessageIndex] = i;
//        updateVideoMessageIndex++;
//        Log.i(TAG, "checkUpdate: need to update count" + i);
//        } else {
//        //无需更新视频
//        Log.i(TAG, "checkUpdate: else" + countNumUpdate);
//        }
//        }
//
//        //整理有效的更新索引值，然后noticefyUpdate
//        int[] updateNum = new int[updateVideoMessageIndex];
//        for (int i = 0; i < updateVideoMessageIndex; i++) {
//        updateNum[i] = updateVideoMessage[i];
//        }