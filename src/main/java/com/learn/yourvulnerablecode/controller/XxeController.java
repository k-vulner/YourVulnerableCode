package com.learn.yourvulnerablecode.controller;

import com.learn.yourvulnerablecode.util.XML_Class;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;

@Controller
public class XxeController {
    @ResponseBody
    @RequestMapping("/xxe")
    public ModelAndView xxe(HttpServletRequest request, HttpServletResponse response){
        String result_xxe="";
        ModelAndView modelAndView = new ModelAndView("/xxe");
        modelAndView.addObject("result_xxe", result_xxe);
        return modelAndView;

    }
    @ResponseBody
    @RequestMapping("/xxe_deal")
    public ModelAndView xxe_deal(HttpServletRequest request, HttpServletResponse response) throws JAXBException, IOException {
        String result_xxe="";
        BufferedReader br = request.getReader();

        String str, wholeStr = "";
        while((str = br.readLine()) != null){
            wholeStr += str;
        }
        System.out.println(wholeStr);
        if(wholeStr!=null){
            switch(request.getParameter("xxe_way")){
                case "jaxb" :
                    /*
                    https://blog.csdn.net/u013041642/article/details/80009175
                    https://blog.csdn.net/jiangchao858/article/details/82355672
                    这个解析方法好像很老，必须引用下面的jar
                    // https://mvnrepository.com/artifact/jakarta.xml.bind/jakarta.xml.bind-api
                    compile group: 'jakarta.xml.bind', name: 'jakarta.xml.bind-api', version: '2.3.2'
                    // https://mvnrepository.com/artifact/org.glassfish.jaxb/jaxb-runtime
                    compile group: 'org.glassfish.jaxb', name: 'jaxb-runtime', version: '2.3.2'
                    JAXBContext 是整个 JAXB API 的入口。
                    主要用来构建 JAXB 实例（newInstance()），并提供与XML/Java绑定信息相关的抽象，如编组（createMarshaller()）、解组（createUnmarshaller()）和验证（createValidator()）。
                     */

                    JAXBContext context = JAXBContext.newInstance(com.learn.yourvulnerablecode.util.XML_Class.class);

                    Unmarshaller unmarshaller = context.createUnmarshaller();
                    Object obj =unmarshaller.unmarshal(new StringReader(wholeStr));
                    //System.out.println(objectToXML(XML_Class.class,obj));
                    break;
                case "dom4j" :

                    break;
                case "dom" :

                    break;
                case "jdom2" :

                    break;
                case "sax" :

                    break;


                default :
                    result_xxe="error";
            }}
        ModelAndView modelAndView = new ModelAndView("/xxe");
        modelAndView.addObject("result_xxe", result_xxe);
        return modelAndView;

    }
}
