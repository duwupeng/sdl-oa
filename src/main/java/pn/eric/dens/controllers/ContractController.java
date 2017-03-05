package pn.eric.dens.controllers;

import com.sun.deploy.net.HttpResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;

@Controller
public class ContractController {

    @Value("${file.attachmentPath}")
    String attachmentPath;
    /**
     * 附件下载
     * @param contractId
     * @param fileName
     * @param fileType
     * @throws Exception
     */
    @RequestMapping(value = "/contract/download/{contractId}/{fileName}/{fileType}",method = RequestMethod.GET)
    public void attachmentDownload(HttpServletResponse res,
                                              @PathVariable int contractId,
                                              @PathVariable String fileName,
                                              @PathVariable String fileType) throws Exception {
        res.setHeader("content-type", "application/octet-stream");
        res.setContentType("application/octet-stream");
        res.setHeader("Content-Disposition", "attachment;filename=" + fileName + "." + fileType);

        File file = new File(attachmentPath+ File.separator + fileName+ "." +fileType);
        InputStream reader = null;
        OutputStream out = null;
        byte[] bytes = new byte[1024];
        int len = 0;
        try {
            // 读取文件
            reader = new FileInputStream(file);
            // 写入浏览器的输出流
            out = res.getOutputStream();

            while ((len = reader.read(bytes)) > 0) {
                out.write(bytes, 0, len);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (reader != null) {
                    reader.close();
                }
                if (out != null)
                    out.close();
            } catch (Exception t) {
                t.printStackTrace();
            }
        }
    }
}
