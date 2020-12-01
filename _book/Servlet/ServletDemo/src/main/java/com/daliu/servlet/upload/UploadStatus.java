package com.daliu.servlet.upload;

/**
 * 进度条信息保存这此Model中
 */
public class UploadStatus {
    /// 已上传的字节数，单位：字节
    private long bytesRead;
    /// 文件总长度，单位：字节
    private long contentLength;
    /// 正在上传第几个文件
    private int items;
    /// 正在上传的时间，用于计算上传速率
    private long startTime = System.currentTimeMillis();
    public long getBytesRead() {
        return bytesRead;
    }
    public void setBytesRead(long bytesRead) {
        this.bytesRead = bytesRead;
    }
    public long getContentLength() {
        return contentLength;
    }
    public void setContentLength(long contentLength) {
        this.contentLength = contentLength;
    }
    public int getItems() {
        return items;
    }
    public void setItems(int items) {
        this.items = items;
    }
    public long getStartTime() {
        return startTime;
    }
    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }
}