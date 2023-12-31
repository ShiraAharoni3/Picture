package org.example;

import javax.imageio.IIOException;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.stream.IntStream;

public class Window extends JFrame
{
    public Window() {
        try
        {
            URL jetImage = getClass().getClassLoader().getResource("jet.jpg");
            if (jetImage != null)
            {
                BufferedImage bufferedImage = contrast(ImageIO.read(jetImage) , 20);
                this.setSize(bufferedImage.getWidth() , bufferedImage.getHeight());
                int color = bufferedImage.getRGB(bufferedImage.getWidth() / 2 , bufferedImage.getHeight() / 2);
                System.out.println("Width: " + bufferedImage.getWidth() );
                System.out.println("Height: " + bufferedImage.getHeight() );
                IntStream.range(0 , bufferedImage.getWidth()).forEach( x->
                        {
                            IntStream.range(0, bufferedImage.getHeight()).forEach(y ->
                            {
                                if (isRed(new Color(bufferedImage.getRGB(x , y)))) {
                                    System.out.println("(" + x + " , " + y + " )");
                                }
                            });
                        });

                Color colorObject = new Color(color) ;
                System.out.println("Red:" + colorObject.getRed() );
                System.out.println("Blue:" + colorObject.getBlue() );
                System.out.println("Green:" + colorObject.getGreen() );
                JLabel label = new JLabel(new ImageIcon(bufferedImage));
                this.add(label);
            }
            else
            {
                System.out.println("Cannot find!");
            }
            this.setLocationRelativeTo(null);
            this.setResizable(false);
            this.setVisible(true);
        }
        catch (IOException e)
        {
            throw new RuntimeException(e);
        }
    }

    public boolean isWhite (Color color)
    {
        return color.getRed()+ color.getBlue() + color.getGreen() > 200 ;
    }

    public boolean isRed (Color color)
    {
        return color.getRed() >  color.getBlue() + color.getGreen()  ;
    }

    public BufferedImage mirror (BufferedImage original)
    {
        BufferedImage processed = new BufferedImage(original.getWidth() ,
                                                  original.getHeight() ,
                                                  BufferedImage.TYPE_INT_RGB);
        for( int i = 0 ; i< original.getHeight() - 1 ; i++)
        {
            for (int j = 0 ; j < original.getWidth() - 1 ; j++)
            {
                processed.setRGB(i , j , original.getRGB(original.getWidth() - i -1  ,  j )) ;
            }
        }
        return processed ;


    }
     public  BufferedImage shuffle (BufferedImage original)
     {
         BufferedImage processed = new BufferedImage(
                 original.getWidth() ,
                 original.getHeight() ,
                 BufferedImage.TYPE_INT_RGB);
         for( int i = 0 ; i< original.getHeight() - 1 ; i++)
         {
             for (int j = 0 ; j < original.getWidth() - 1 ; j++)
             {
                 Color color = new Color(original.getRGB(i , j)) ;
                 int red = color.getRed() ;
                 int blue = color.getBlue() ;
                 int green = color.getGreen() ;
                 Color newColor = new Color(blue , green , red) ;
                 processed.setRGB(i , j , newColor.getRGB());

              }
         }
         return processed ;



     }

     public BufferedImage grayScale (BufferedImage bufferedImage)
     {
         BufferedImage grayS = new BufferedImage( bufferedImage.getWidth() ,
                                                  bufferedImage.getHeight() ,
                                                  BufferedImage.TYPE_INT_RGB);
         for( int i = 0 ; i< bufferedImage.getHeight() - 1 ; i++)
         {
             for (int j = 0 ; j < bufferedImage.getWidth() - 1 ; j++)
             {
                 Color color = new Color(bufferedImage.getRGB(i , j)) ;
                 int red = color.getRed() ;
                 int blue = color.getBlue() ;
                 int green = color.getGreen() ;
                 int average = (red + blue + green) / 3 ;
                 int max = 0 ;
                 if (red > blue) {
                     if (red > green) {
                         max = red;
                     }
                 }
                 else if (blue > red) {
                     if (blue > green) {
                         max = blue;
                     }
                 }
                 else
                   max = green ;

                 {
                     Color newColor = new Color(average , average , average) ;
                     grayS.setRGB(i , j , newColor.getRGB());
                 }

                 }


             }
         return grayS ;
     }

    public BufferedImage negative (BufferedImage bufferedImage)
    {
        int maxColor = 255 ;
        BufferedImage grayS = new BufferedImage( bufferedImage.getWidth() ,
                bufferedImage.getHeight() ,
                BufferedImage.TYPE_INT_RGB);
        for( int i = 0 ; i< bufferedImage.getHeight() - 1 ; i++)
        {
            for (int j = 0 ; j < bufferedImage.getWidth() - 1 ; j++)
            {
                Color color = new Color(bufferedImage.getRGB(i , j)) ;
                int red = color.getRed() ;
                int blue = color.getBlue() ;
                int green = color.getGreen() ;
                Color newColor = new Color(maxColor - red, maxColor - blue , maxColor - green);
            }


        }
        return grayS ;
    }
    public BufferedImage contrast (BufferedImage bufferedImage , float by)
    {
        BufferedImage grayS = new BufferedImage( bufferedImage.getWidth() ,
                bufferedImage.getHeight() ,
                BufferedImage.TYPE_INT_RGB);
        for( int i = 0 ; i< bufferedImage.getHeight() - 1 ; i++)
        {
            for (int j = 0 ; j < bufferedImage.getWidth() - 1 ; j++)
            {
                Color color = new Color(bufferedImage.getRGB(i , j)) ;
                int red = color.getRed() ;
                int blue = color.getBlue() ;
                int green = color.getGreen() ;

                Color newColor = new Color(itensify(red , by) ,itensify(blue , by) , itensify(green , by) );
            }


        }
        return grayS ;
    }
    public int itensify (int original , float by)
    {
        if (original > 128)
        {
            original *= (100 + by) /100 ;
        }
        else
        {
            original *= (100 - by) /100 ;
        }
        return Math.min(255 , original ) ;

    }

          /*  try {
                URL jetImage = getClass().getClassLoader().getResource("jet.jpg");
                if (jetImage != null) {
                    BufferedImage bufferedImage = drawEdges(ImageIO.read(jetImage));
                    this.setSize(bufferedImage.getWidth(), bufferedImage.getHeight());
                    int color = bufferedImage.getRGB(bufferedImage.getWidth() / 2, bufferedImage.getHeight() / 2);
                    System.out.println("Width: " + bufferedImage.getWidth());
                    System.out.println("Height: " + bufferedImage.getHeight());
                    IntStream.range(0, bufferedImage.getWidth()).forEach(x -> {
                        IntStream.range(0, bufferedImage.getHeight()).forEach(y -> {
                            if (isRedish(new Color(bufferedImage.getRGB(x, y)))) {
                                System.out.println("(" + x + " , " + y + ")");
                            }
                        });
                    });
                    Color colorObject = new Color(color);
                    System.out.println("RED:" + colorObject.getRed());
                    System.out.println("GREEN: " + colorObject.getGreen());
                    System.out.println("BLUE: " + colorObject.getBlue());
                    JLabel label = new JLabel(new ImageIcon(bufferedImage));
                    this.add(label);
                } else {
                    System.out.println("Cannot find!");
                }
                this.setLocationRelativeTo(null);
                this.setResizable(false);
                this.setVisible(true);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } */


        public BufferedImage drawEdges (BufferedImage original) { // מוצא את הקצוות של התמונה 
            BufferedImage output = new BufferedImage(original.getWidth(), original.getHeight(), original.getType());
            IntStream.range(1, original.getWidth() - 1).forEach( x -> {
                IntStream.range(1, original.getHeight() - 1).forEach(y -> {
                    Color current = new Color(original.getRGB(x, y));
                    Color north = new Color(original.getRGB(x, y + 1));
                    Color south = new Color(original.getRGB(x, y - 1));
                    Color east = new Color(original.getRGB(x - 1, y));
                    Color west = new Color(original.getRGB(x + 1, y));
                    if (isDifferent(current, north) ||
//                        isDifferent(current, south) ||
//                        isDifferent(current, east) ||
                            isDifferent(current, west)) {
                        output.setRGB(x, y, Color.BLUE.getRGB());
                    } else {
                        output.setRGB(x, y, original.getRGB(x, y));

                    }
                });
            });
            return output;
        }

        private boolean isDifferent (Color color1, Color color2) {
            boolean different = false;
            int redDiff = Math.abs(color1.getRed() - color2.getRed());
            int greenDiff = Math.abs(color1.getGreen() - color2.getGreen());
            int blueDiff = Math.abs(color1.getBlue() - color2.getBlue());
            if (redDiff + greenDiff + blueDiff > 30) {
                different = true;
            }
            return different;
        }

        public boolean isBlackish (Color color) {
            return color.getRed() + color.getGreen() + color.getBlue() < 50;
        }

        public boolean isRedishShay (Color color) {
            return color.getRed() > 160 && color.getGreen() < 100 && color.getBlue() < 100;
        }

        public BufferedImage mirrorShay(BufferedImage original)
        {
            BufferedImage processed = new BufferedImage(
                    original.getWidth(),
                    original.getHeight(),
                    BufferedImage.TYPE_INT_RGB);
            for (int i = 0; i < original.getHeight() - 1; i++) {
                for (int j = 0; j < original.getWidth() - 1; j++) {
                    processed.setRGB(i, j, original.getRGB(original.getWidth() - i - 1, j));
                }
            }
            return processed;
        }

        public BufferedImage grayScaleShay (BufferedImage original)
        {
            BufferedImage processed = new BufferedImage(
                    original.getWidth(),
                    original.getHeight(),
                    BufferedImage.TYPE_INT_RGB);
            for (int i = 0; i < original.getHeight() - 1; i++) {
                for (int j = 0; j < original.getWidth() - 1; j++) {
                    Color color = new Color(original.getRGB(i, j));
                    int red = color.getRed();
                    int green = color.getGreen();
                    int blue = color.getBlue();
                    int average = (red + green + blue) / 3;
                    Color newColor = new Color(average, average, average);
                    processed.setRGB(i, j, newColor.getRGB());
                }
            }
            return processed;

        }

        public BufferedImage shuffleShay (BufferedImage original) {
            BufferedImage processed = new BufferedImage(
                    original.getWidth(),
                    original.getHeight(),
                    BufferedImage.TYPE_INT_RGB);
            for (int i = 0; i < original.getHeight() - 1; i++) {
                for (int j = 0; j < original.getWidth() - 1; j++) {
                    Color color = new Color(original.getRGB(i, j));
                    int red = color.getRed();
                    int green = color.getGreen();
                    int blue = color.getBlue();
                    Color newColor = new Color(blue, green, red);
                    processed.setRGB(i, j, newColor.getRGB());
                }
            }
            return processed;
        }


        public BufferedImage negativeShay (BufferedImage original) {
            BufferedImage processed = new BufferedImage(
                    original.getWidth(),
                    original.getHeight(),
                    BufferedImage.TYPE_INT_RGB);
            for (int i = 0; i < original.getHeight() - 1; i++) {
                for (int j = 0; j < original.getWidth() - 1; j++) {
                    Color color = new Color(original.getRGB(i, j));
                    int red = color.getRed();
                    int green = color.getGreen();
                    int blue = color.getBlue();
                    Color newColor = new Color(255 - red, 255 - green, 255  - blue);
                    processed.setRGB(i, j, newColor.getRGB());
                }
            }
            return processed;

        }



        public BufferedImage contrast (BufferedImage original, int by) {
            BufferedImage processed = new BufferedImage(
                    original.getWidth(),
                    original.getHeight(),
                    BufferedImage.TYPE_INT_RGB);
            for (int i = 0; i < original.getHeight() - 1; i++) {
                for (int j = 0; j < original.getWidth() - 1; j++) {
                    Color color = new Color(original.getRGB(i, j));
                    int red = color.getRed();
                    int green = color.getGreen();
                    int blue = color.getBlue();
                    Color newColor = new Color(itensify(red, by), itensify(green, by), itensify(blue, by));
                    processed.setRGB(i, j, newColor.getRGB());
                }
            }
            return processed;

        }

        public int itensify (int original, int by) {
            if (original > 128) {
                original *= ((100 + by) / 100);
            } else {
                original *= ((100 - by) / 100);
            }
            return Math.min(255, original);

        }

    }

