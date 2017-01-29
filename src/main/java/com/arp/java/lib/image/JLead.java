package com.arp.java.lib.image;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.commons.cli.BasicParser;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

import com.sun.image.codec.jpeg.ImageFormatException;
import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGEncodeParam;
import com.sun.image.codec.jpeg.JPEGImageDecoder;
import com.sun.image.codec.jpeg.JPEGImageEncoder;

/**
 * このクラスは、JPEGファイルに対して様々な機能を提供するクラスです。
 *
 * @author Haruo TAKEDA
 */
public class JLead {
    /** エラーがない状態を示すフィールドの値です。 */
    public static final int     NO_ERROR = 0;

    /** エラーがある状態を示すフィールドの値です。 */
    public static final int     ERROR = 1;

    /** 最高画質を示すフィールドの値です。 */
    public static final float EXCELENT = 1.0f;

    /** 高画質を示すフィールドの値です。 */
    public static final float HIGHT = 0.75f;

    /** 標準画質を示すフィールドの値です。 */
    public static final float NORMAL = 0.5f;

    /** 低画質を示すフィールドの値です。 */
    public static final float LOW = 0.25f;

    /** 最低画質を示すフィールドの値です。 */
    public static final float LESS = 0.0f;

    /** デフォルトのイメージスケーリングアルゴリズムを示すフィールドの値です。 */
    public static final int DEFAULT = Image.SCALE_DEFAULT;

    /** スケーリング速度よりもイメージの滑らかさに高い優先順位を与えるイメージスケーリングアルゴリズムを示すフィールドの値です。 */
    public static final int SMOOTH = Image.SCALE_SMOOTH;

    /** スケーリング後のイメージの滑らかさよりもスケーリング速度に高い優先順位を与えるイメージスケーリングアルゴリズムを示すフィールドの値です。 */
    public static final int FAST = Image.SCALE_FAST;

    /**
     * サムネイルファイルを作成します。 指定したJPEGファイルを、指定したファイル名でサムネイルファイルを作成します。
     *
     * @param inputFile
     *            JPEGファイル名をフルパスで指定します。
     * @param outputFile
     *            出力先のサムネイルファイル名をフルパスで指定します。
     * @param newHeight
     *            作成するサムネイルファイルの高さ(ピクセル数）を指定します。
     * @param scale
     *            サムネイルを作成する際のイメージスケーリングアルゴリズムを指定します。
     * @param quality
     *            品質レベルを指定します。
     * @param description
     *            画像下部に表示するコメントを指定します。 NULL の場合表示されません。
     * @result 
     *          ステータスコードを返します。
     * @throws IllegalArgumentException
     *            無効な値を指定した場合にスローされます。
     * @throws JLeadException
     *             以下の場合にスローされます。<br>
     *             指定したファイルが存在しなかった場合。<br>
     *             指定したファイルがJPEGファイルではない場合や、フォーマット異常の時。<br>
     *             ファイル出力時に入出力エラーが発生した場合。
     */
    public static int createThumbnails(String inputFile, String outputFile,
            int newHeight, int scale, float quality, String description)
            throws IllegalArgumentException, JLeadException {
        BufferedImage bufferedImage;
        BufferedImage newBufferedImage;
        Graphics2D bufferedScreen;
        int w, h;

        if (inputFile == null) {
            throw new IllegalArgumentException();
        }
        
        try {
            bufferedImage = decode(inputFile);

            w = (bufferedImage.getWidth() * newHeight)
                    / bufferedImage.getHeight();
            Image newimage = bufferedImage.getScaledInstance(-1, newHeight,
                    Image.SCALE_SMOOTH);

            w = newimage.getWidth(null);
            h = newimage.getHeight(null);

            newBufferedImage = new BufferedImage(w, h,
                    BufferedImage.TYPE_INT_RGB);
            bufferedScreen = newBufferedImage.createGraphics();
            bufferedScreen.drawImage(newimage, 0, 0, w, h, null);

            if (description != null) {
                bufferedScreen.setColor(Color.black);
                bufferedScreen.fillRect(0, newHeight - 20, w, h);
                description = "Created by JImage Scale=" + scale + " Quality="
                        + quality + " : " + description;
                bufferedScreen.setColor(Color.yellow);
                bufferedScreen.drawString(description, 10, newHeight - 5);
            }

            JPEGImageEncoder enc = JPEGCodec
                    .createJPEGEncoder(new FileOutputStream(
                            new File(outputFile)));
            JPEGEncodeParam jep;
            jep = enc.getDefaultJPEGEncodeParam(newBufferedImage);
            jep.setQuality(quality, false);
            enc.setJPEGEncodeParam(jep);
            enc.encode(newBufferedImage);
        } catch (FileNotFoundException e) {
            throw new JLeadException(e.getMessage());
        } catch (ImageFormatException e) {
            throw new JLeadException(e.getMessage());
        } catch (IOException e) {
            throw new JLeadException(e.getMessage());
        } catch (Exception e) {
            throw new JLeadException(e.getMessage());
        }
        return NO_ERROR;
    }

    private static BufferedImage decode(String inFilename)
            throws FileNotFoundException, IOException, ImageFormatException {
        FileInputStream in;
        BufferedImage bimage = null;

        in = new FileInputStream(inFilename);
        JPEGImageDecoder decoder = JPEGCodec.createJPEGDecoder(in);
        bimage = decoder.decodeAsBufferedImage();
        in.close();
        decoder = null;
        return bimage;
    }

    public static void main(String[] argv) {
        Options options = new Options();

        options.addOption("i", "input", true, "JPEG source file path");
        options.addOption("o", "output", true, "JPEG Thumbnail file path file");
        options.addOption("h", "height", true, "JPEG Thumbnail image height");
        options.addOption(
                "s",
                "scale",
                false,
                "flags to indicate the type of algorithm to use for image resampling.[FAST,DEFAULT,SMOOTH]");
        options.addOption("q", "quality", true,
                "a float between 0 and 1 indicating the desired quality level");
        options.addOption("d", "desc", false, "description");

        CommandLineParser parser = new BasicParser();
        CommandLine commandLine;

        String inFilename = null;
        String outFilename = null;
        int newHeight = 0;
        int scale = JLead.DEFAULT;
        float quality = 0;
        String description = null;
        String scalestr = null;

        try {
            commandLine = parser.parse(options, argv);

            if (commandLine.hasOption("i")) {
                inFilename = commandLine.getOptionValue("i");
            }

            if (commandLine.hasOption("o")) {
                outFilename = commandLine.getOptionValue("o");
            }

            if (commandLine.hasOption("h")) {
                newHeight = Integer.parseInt(commandLine.getOptionValue("h"));
            }

            if (commandLine.hasOption("s")) {
                scalestr = commandLine.getOptionValue("s");
                if ("FAST".compareToIgnoreCase(scalestr) == 0) {
                    scale = JLead.FAST;
                } else if ("DEFAULT".compareToIgnoreCase(scalestr) == 0) {
                    scale = JLead.DEFAULT;
                } else if ("SMOOTH".compareToIgnoreCase(scalestr) == 0) {
                    scale = JLead.SMOOTH;
                } else {
                    System.out.println("It is an invalid setting value. "
                            + scalestr);
                    System.exit(0);
                }
            }
            if (commandLine.hasOption("q")) {
                quality = Float.parseFloat(commandLine.getOptionValue("q"));
            }
            if (commandLine.hasOption("d")) {
                description = commandLine.getOptionValue("d");
            }
            JLead.createThumbnails(inFilename, outFilename, newHeight, scale,
                    quality, description);
        } catch (ParseException | IllegalArgumentException | JLeadException e) {
            e.printStackTrace();
            HelpFormatter formatter = new HelpFormatter();
            formatter.printHelp("java -jar JLead", options);
        }

        System.exit(0);
    }
}
