package vn.vimass.service.crawler.bhd.XuLyDataExcel;

import vn.vimass.service.log.FileManager;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

public class ExcelControlller {

    public Response getDataExcel() {
        File file = new File("D:\\VPass13122023\\data\\bangCong_30-01-2024.xlsx");
        Response.ResponseBuilder rb = Response.ok(file);
        rb.header("content-disposition", "attachment; filename=bangCong_30-01-2024.xlsx");
        return rb.build();
    }

//    @POST
//    @Path("/recviceExcel")
//    @Produces(MediaType.APPLICATION_OCTET_STREAM)
//    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public Response recviceDataExcel(InputStream excelInputStream) {
        try {
            // Save the uploaded Excel file to a temporary file
            File tempFile = File.createTempFile("uploadedExcel", ".xlsx");
            Files.copy(excelInputStream, tempFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
            // Process the Excel file (e.g., read, manipulate, etc.)
            // Implement your logic here
            // For demonstration, let's just return the same file as response

            String str = FileManager.readFileNormalPost(tempFile);
            parseData(str);
            return Response.ok(tempFile, MediaType.APPLICATION_OCTET_STREAM)
                    .header("Content-Disposition", "attachment; filename=\"" + tempFile.getName() + "\"")
                    .build();
        } catch (IOException e) {
            e.printStackTrace();
            return Response.serverError().build();
        }

    }

    private static void parseData(String data) {

        try {
            String[] lines = data.split("\n");
            String vID = "";
            String name = "";
            for (int i = 2; i < lines.length; i++) {
                String[] strs = lines[i].split(",");
            }

        } catch (Exception e) {
            System.out.println("Exception: " + e.getMessage());
        }


    }

}
