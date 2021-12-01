package com.phoenix.logistics.util;

import com.phoenix.logistics.mapper.CompanyMapper;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import com.phoenix.logistics.common.EnumExceptionType;
import com.phoenix.logistics.entity.Tb_company;
import com.phoenix.logistics.exception.RRException;

import java.io.*;
import java.sql.Timestamp;

@Setter
@Component
public class MultipartFileUtil {

    @Value("${filePath.company.logo}")
    private String logoPath;

    @Value("${filePath.company.logoUrl}")
    private String logoPathUrl;

    @Value("${filePath.company.license}")
    private String licensePath;

    @Value("${filePath.company.authorization}")
    private String authorizationPath;

    @Value("${filePath.company.contentFile}")
    private String contentFilePath;

    @Value("${filePath.company.contentFileUrl}")
    private String contentFilePathUrl;

    @Value("${filePath.company.contentPicture}")
    private String contentPicturePath;

    @Value("${filePath.company.contentPictureUrl}")
    private String contentPicturePathUrl;

    @Autowired
    CompanyMapper companyMapper;

    public String saveAuthorization(MultipartFile authorization,long company_id) throws IOException {
        if(authorization == null)return "";
        File file = new File(authorizationPath);
        if(!file.isDirectory()){
            file.mkdirs();
        }
        String url = authorizationPath + "/" + company_id + ".png";
        saveFile(authorization,url);
        return url;
    }

    public String saveLicense(MultipartFile license,long company_id) throws IOException {
        if(license == null)return "";
        File file = new File(licensePath);
        if(!file.isDirectory()){
            file.mkdirs();
        }
        String url = licensePath + "/" + company_id + ".png";
        saveFile(license,url);
        return url;
    }

    public String saveLogo(MultipartFile logo,long company_id) throws IOException {
        if(logo == null)return "";
        File file = new File(logoPath);
        if(!file.isDirectory()){
            file.mkdirs();
        }
        String url = logoPath + "/" + company_id + ".png";
        saveFile(logo,url);
        url = logoPathUrl + "/" + company_id + ".png";
        return url;
    }

    public String saveContentFile(MultipartFile contentFile,long company_id) throws IOException {
        if(contentFile == null)return "";
        File file = new File(contentFilePath);
        if(!file.isDirectory()){
            file.mkdirs();
        }

        //获取文件后缀名
        String postfix;
        try{
            String s = contentFile.getOriginalFilename();
            int i = s.lastIndexOf(".");
            postfix = s.substring(i+1,s.length());
        }catch (Exception e){
            throw new RRException(EnumExceptionType.FILE_ILLEGAL);
        }

        //改文件名
        String fileName = "";
        Tb_company company = companyMapper.getCompanyById(company_id);
        fileName += company.getCompany_name();
        fileName += "+";
        fileName += company.getCompany_contacts();
        fileName += "+";
        fileName += company.getCompany_mobile();
        fileName += "+";

        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        String timeStr=timestamp
                .toString()
                .substring(0, timestamp.toString().indexOf(" "));
        fileName += timeStr;


        String url = contentFilePath + "/" + fileName + "." + postfix;
        saveFile(contentFile,url);
        url = contentFilePathUrl + "/" + fileName + "." + postfix;
        return url;
    }

    public String saveContentPicture(MultipartFile contentPicture,long company_id) throws IOException {
        if(contentPicture == null)return "";
        File file = new File(contentPicturePath);
        if(!file.isDirectory()){
            file.mkdirs();
        }

        //获取文件后缀名
        String postfix;
        String prefix;
        try{
            String s = contentPicture.getOriginalFilename();
            int i = s.lastIndexOf(".");
            postfix = s.substring(i+1,s.length());
            prefix = s.substring(0,i);

            //检查prefix。必须是整数
            int i1 = Integer.parseInt(prefix);
        }catch (Exception e){
            throw new RRException(EnumExceptionType.FILE_ILLEGAL);
        }
        String url = contentPicturePath + "/" + company_id + "_" + prefix + "." + postfix;
        saveFile(contentPicture,url);
        url = contentPicturePathUrl + "/" + company_id + "_" + prefix + "." + postfix;
        return url;
    }


    private void saveFile(MultipartFile multipartFile,String url) throws IOException {
        if(multipartFile == null)return;
        InputStream inputStream = multipartFile.getInputStream();
        OutputStream os = null;

        try {
            // 2、保存到临时文件
            // 1K的数据缓冲
            byte[] bs = new byte[1024];
            // 读取到的数据长度
            int len;
            // 输出的文件流保存到本地文件

            File tempFile = new File(url);
            System.out.println(tempFile.getAbsolutePath());
            if(!tempFile.exists()){
                tempFile.createNewFile();
            }
            os = new FileOutputStream(url);
            // 开始读取
            while ((len = inputStream.read(bs)) != -1) {
                os.write(bs, 0, len);
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            // 完毕，关闭所有链接
            try {
                os.close();
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }



}
