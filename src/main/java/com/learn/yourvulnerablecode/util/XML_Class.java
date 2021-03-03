package com.learn.yourvulnerablecode.util;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "AAA")
@XmlAccessorType(XmlAccessType.FIELD)
public class XML_Class {
    @XmlElement(name = "aaa")
    private String aaa;

}
