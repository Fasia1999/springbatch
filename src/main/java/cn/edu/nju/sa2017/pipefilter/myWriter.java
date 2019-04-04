package cn.edu.nju.sa2017.pipefilter;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import org.springframework.core.io.Resource;
import org.springframework.batch.item.ItemWriter;

public class myWriter implements ItemWriter<Report> {

    private Resource resource;
    public void setResource(Resource resource) {
        this.resource = resource;
    }

    @Override
    public void write(List<? extends Report> items) throws Exception {
        //System.out.println("writer chunk size is :" + items.size());
        FileWriter fw;
        String fileDes = resource.toString();
        String str = fileDes.substring(fileDes.indexOf('[')+6,fileDes.indexOf(']'));
        try {
            fw = new FileWriter(str);
            PrintWriter out = new PrintWriter(fw);
            out.println("{\n \"reports\":{");
            out.println("  \"record\":[");
            int i = 0;
            for(Report item:items)
            {
                out.println("   {");
                out.println("    \"id\":" + "\"" + item.getId() + "\",");
                out.println("    \"sales\":" + "\"" + item.getSales() + "\",");
                out.println("    \"qty\":" + "\"" + item.getQty() + "\",");
                out.println("    \"staffname\":" + "\"" + item.getStaffName() + "\",");
                out.println("    \"date\":" + "\"" + item.getDate() + "\"");
                out.print("   }");
                i++;
                if(i < items.size())
                    out.println(",");
                else out.println("");
            }
            //out.print("\b");
            out.println("  ]");
            out.println(" }");
            out.println("}");
            fw.close();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}