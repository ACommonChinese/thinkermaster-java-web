package com.daliu.servlet.upload;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

public class ProgressUploadServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // UploadStatus是一个java bean封装进度相关信息
        UploadStatus status = new UploadStatus();
        UploadListener listener = new UploadListener(status); // 监听器
        // 把状态放入Session里
        /**
         * HttpSession getSession(boolean create)
         * Returns the current HttpSession associated with this request or, if there is no current session and create is true, returns a new session.
         * If create is false and the request has no valid HttpSession, this method returns null.
         * 把上传进度保存在Session的uploadStatus属性中
         */
        request.getSession(true).setAttribute("uploadStatus", status);

        ServletFileUpload upload = new ServletFileUpload(new DiskFileItemFactory());
        upload.setProgressListener(listener); // 设置上传组件监听器：把监听器和上传组件关联起来

        List<FileItem> itemList = null;
        try {
            itemList = upload.parseRequest(request);
        } catch (Exception ex) {
            System.out.println("parse request exception");
            ex.printStackTrace();
        }

        try {
            Thread.currentThread().sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        for (Iterator<FileItem> iterator = itemList.iterator(); iterator.hasNext();) {
            FileItem item = iterator.next();
            if (item.isFormField()) { // 普通表单数据
                System.out.println("FormField: " + item.getFieldName() + " = " + item.getString("utf-8"));
            } else {
                // 上传的是文件
                System.out.println("File: " + item.getName());
                String filesPath = this.getServletContext().getRealPath("/files");
                // /Users/daliu-macbook/Documents/.../ServletDemo/out/artifacts/demo/files
                // 统一Linux与windows的路径分隔符
                String fileName = item.getName().replace("\\", "/");
                if (fileName.length() > 0 && fileName.contains("/")) {
                    fileName = fileName.substring(fileName.lastIndexOf("/"));
                }
                String child = UUID.randomUUID() + "_" + fileName;
                File saveFile = new File(filesPath, child);
                saveFile.getParentFile().mkdirs(); // 确保保存路径存在
                InputStream inputStream = item.getInputStream();
                OutputStream outputStream = new FileOutputStream(saveFile);
                byte[] tmp = new byte[1024];
                int len = -1;
                while ((len = inputStream.read(tmp)) != -1) {
                    outputStream.write(tmp, 0, len);
                }
                outputStream.close();
                inputStream.close();
                PrintWriter out = response.getWriter();
                out.println("已保存文件: " + saveFile);
                out.flush();
                out.close();
            }
        }
    }

    @Override
    /**
     * 使用get方法读取上传进度
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 设置编码格式
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");

        // 禁止使用浏览器缓存
        response.setHeader("Pragma", "no-cache");
        response.setDateHeader("Expires", -1);
        response.setHeader("Cache-Control", "no-cache");

        UploadStatus status = (UploadStatus)request.getSession(true).getAttribute("uploadStatus");
        if (status == null) {
            response.getWriter().println("没有上传信息");
            return;
        }

        long startTime = status.getStartTime(); // 上传开始时间
        long currentTime = System.currentTimeMillis(); // 当前时间
        long time = (currentTime - startTime) / 1000 + 1; // 已传输时间，单位：秒
        double velocity = ((double)status.getBytesRead()) / (double)time; // 传输速度，单位 byte/s
        double totalTime = status.getContentLength() / velocity; // 估计总时间，单位 s
        double timeLeft = totalTime - time;

        // 已完成百分比
        int percent = (int)( 100 * (double)status.getBytesRead() / (double)status.getContentLength() );

        // 已完成数，单位：M
        double length = ((double)status.getBytesRead())/1024/1024;

        // 总长度，单位：M
        double totalLength = ((double) status.getContentLength())/1024/1024;

        // 格式：已完成百分比||已完成数(M)||文件总长度(M)||传输速率(K)已用时间(s)||估计总时间(s)||估计剩余时间(s)||正在上传第几个文件
        String value = percent + "||" +
                length + "||" +
                totalLength + "||" +
                velocity + "||" +
                time + "||" +
                totalTime + "||" +
                timeLeft + "||" +
                status.getItems();
        PrintWriter out = response.getWriter();
        out.write(value);
        out.flush();
        out.close();
    }
}
