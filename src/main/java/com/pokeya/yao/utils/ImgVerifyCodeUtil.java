package com.pokeya.yao.utils;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Base64;
import java.util.Base64.Encoder;
import java.util.Random;

/**
 * @author 王超
 * @date 2020/4/23 11:03
 */
public class ImgVerifyCodeUtil {
  // 定义图形验证码中绘制的字符的字体
  private static final Font mFont = new Font("Arial Black", Font.PLAIN, 23);
  // 图形验证码的大小
  private static final int IMG_WIDTH = 72;
  private static final int IMG_HEIGTH = 27;

  public static String generateImgCode(String code) throws IOException {
    // 设置禁止缓存
    BufferedImage image = new BufferedImage(IMG_WIDTH, IMG_HEIGTH, BufferedImage.TYPE_INT_RGB);
    Graphics g = image.getGraphics();
    Random random = new Random();
    // 填充背景色
    g.setColor(getRandColor(200, 250));
    g.fillRect(1, 1, IMG_WIDTH - 1, IMG_HEIGTH - 1);

    // 为图形验证码绘制边框
    g.setColor(new Color(102, 102, 102));
    g.drawRect(0, 0, IMG_WIDTH, IMG_HEIGTH);

    // 生成随机干扰线
    g.setColor(getRandColor(160, 200));
    for (int i = 0; i < 80; i++) {
      int x = random.nextInt(IMG_WIDTH - 1);
      int y = random.nextInt(IMG_HEIGTH - 1);
      int x1 = random.nextInt(6) + 1;
      int y1 = random.nextInt(12) + 1;
      g.drawLine(x, y, x + x1, y + y1);
    }

    // 生成随机干扰线
    g.setColor(getRandColor(160, 200));
    for (int i = 0; i < 80; i++) {
      int x = random.nextInt(IMG_WIDTH - 1);
      int y = random.nextInt(IMG_HEIGTH - 1);
      int x1 = random.nextInt(12) + 1;
      int y1 = random.nextInt(6) + 1;
      g.drawLine(x, y, x - x1, y - y1);
    }

    // 设置绘制字符的字体
    g.setFont(mFont);
    for (int i = 0; i < code.length(); i++) {
      char c = code.charAt(i);
      g.setColor(new Color(20 + random.nextInt(110), 20 + random.nextInt(110), 20 + random.nextInt(110)));
      g.drawString(String.valueOf(c), 15 * i + 10, 20);
    }

    g.dispose();
    // 向输出流中输出图片
    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    ImageIO.write(image, "PNG", outputStream);

    byte[] bytes = outputStream.toByteArray();
    Encoder encoder = Base64.getEncoder();
    String img = encoder.encodeToString(bytes).trim().replaceAll("\n", "").replaceAll("\r", "");
    return "data:image/png;base64," + img;
  }


  // 获取随机颜色的方法
  private static Color getRandColor(int fc, int bc) {
    Random random = new Random();
    if (fc > 255)
      fc = 255;
    if (bc > 255)
      bc = 255;
    int r = fc + random.nextInt(bc - fc);
    int g = fc + random.nextInt(bc - fc);
    int b = fc + random.nextInt(bc - fc);
    return new Color(r, g, b);
  }
}
