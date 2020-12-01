package com.daliu.servlet.upload;

import org.apache.commons.fileupload.ProgressListener;

public class UploadListener implements ProgressListener {
    private UploadStatus status;
    public UploadListener(UploadStatus status) {
        this.status = status;
    }

    @Override
    public void update(long bytesRead, long contentLength, int items) {
        // 由于上传组件和此Listener进行了关联，因此上传组件会反复调用此方法
        status.setBytesRead(bytesRead);
        status.setContentLength(contentLength);
        status.setItems(items); // items表示正在上传第几个文件
        System.out.println("hasRead: " + bytesRead);
    }
}
