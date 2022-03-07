
//package blur;
import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import parcs.*;
import java.awt.image.BufferedImage;
import java.awt.Image;
import java.util.*;
import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import javax.imageio.ImageIO;

public class Main implements AM {
    
    public static int threads = 4;
    public static void main(String[] args){	
        task mainTask = new task();
        mainTask.addJarFile("Algorithm.jar");
        mainTask.addJarFile("Main.jar");
        (new Main()).run(new AMInfo(mainTask, (channel)null));
        mainTask.end();
    }
    
    @Override
    public void run(AMInfo info){
        String link;
        int radius;
        try
        {
            BufferedReader in = new BufferedReader(new FileReader(info.curtask.findFile("input.txt")));
            link = in.readLine();
            radius = Integer.parseInt(in.readLine());
            
            System.out.println("link = "+link);
            System.out.println("rad = "+radius);
        }
        catch (IOException e)
        {
            System.out.print("Error while reading input\n");
            return;
        }
        System.out.println("Read file: successful.");
        long tStart = System.nanoTime();
        solve(info, link, radius);
        long tEnd = System.nanoTime();
        System.out.println("time = " + ((tEnd - tStart) / 1000000) + "ms");
    }
    
    public static void solve(AMInfo info, String imageUrl, int radius)
    {
        List<BufferedImage> reses = new ArrayList<>();
        List<point> points = new ArrayList<>();
        List<channel> channels = new ArrayList<>();
        
        //read img and split
        BufferedImage input = null;
        try
        {
            System.setProperty("http.agent", "Chrome");
            URL url = new URL(imageUrl);
            URLConnection conn = url.openConnection();
            InputStream in = conn.getInputStream();
            input = ImageIO.read(in);
            input.coerceData(true);
            System.out.println("Input image read successfully.");
            
        for (int i = 0; i < threads; i++){
            points.add(info.createPoint());
            channels.add(points.get(i).createChannel());
            points.get(i).execute("Algorithm");
            channels.get(i).write(new Image_SRZ(input));
            channels.get(i).write(radius);
            System.out.println(i+" sent.");
        }
        
        for(int i = 0; i < threads; i++){
            System.out.println(i+" starts");
            var image = ((Image_SRZ)channels.get(i).readObject()).getImage();
            System.out.println(i+" ends");
            reses.add(image);
        }
        
        }
        catch(Exception e)
        {
            e.printStackTrace(System.out);
        }
        
    }
}
