package com.jdd.imadmin.common.util;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Base64;
import java.util.Hashtable;

/**
 * @author @sailength on 2020/3/23.
 */
public class QrCodeUtils {


    public final static String groupString = "DXIM://group/%s/%s";

    public final static String serviceString = "DXIM://officeAccount/%s";

    public final static String PREFIX="data:image/jpeg;base64,";

    public static String createQrCode(String content, int qrCodeSize) throws WriterException, IOException {
        //设置二维码纠错级别ＭＡＰ
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        Hashtable<EncodeHintType, ErrorCorrectionLevel> hintMap = new Hashtable<EncodeHintType, ErrorCorrectionLevel>();
        hintMap.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L);  // 矫错级别
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        //创建比特矩阵(位矩阵)的QR码编码的字符串
        BitMatrix byteMatrix = qrCodeWriter.encode(content, BarcodeFormat.QR_CODE, qrCodeSize, qrCodeSize, hintMap);
        // 使BufferedImage勾画QRCode  (matrixWidth 是行二维码像素点)
        int matrixWidth = byteMatrix.getWidth();
        BufferedImage image = new BufferedImage(matrixWidth - 200, matrixWidth - 200, BufferedImage.TYPE_INT_RGB);
        image.createGraphics();
        Graphics2D graphics = (Graphics2D) image.getGraphics();
        graphics.setColor(Color.WHITE);
        graphics.fillRect(0, 0, matrixWidth, matrixWidth);
        // 使用比特矩阵画并保存图像
        graphics.setColor(Color.BLACK);
        for (int i = 0; i < matrixWidth; i++) {
            for (int j = 0; j < matrixWidth; j++) {
                if (byteMatrix.get(i, j)) {
                    graphics.fillRect(i - 100, j - 100, 1, 1);
                }
            }
        }
        ImageIO.write(image, "JPEG", byteArrayOutputStream);
        byte[] bytes = byteArrayOutputStream.toByteArray();
        return PREFIX+Base64.getEncoder().encodeToString(bytes);

    }


    public static String getServiceQr(String serviceId) throws IOException, WriterException {
        return createQrCode(String.format(serviceString, serviceId), 1000);
    }

    public static String getGroupQr(String groupId, String uuid) throws IOException, WriterException {
        return createQrCode(String.format(groupString, groupId, uuid), 1000);
    }

    public static void main(String args[]) throws IOException, WriterException {
        String content=getServiceQr("XCZdXdtt");
        System.out.println(content);
    }
}
