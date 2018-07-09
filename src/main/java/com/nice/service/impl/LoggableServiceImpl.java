package com.nice.service.impl;

import com.nice.service.LoggableService;
import org.springframework.stereotype.Service;

/**
 * @program: nice-springboot
 * @description:
 * @author: BaoFei
 * @create: 2018-07-09 10:45
 **/
@Service
public class LoggableServiceImpl implements LoggableService{

    @Override
    public void log() {
        System.out.println(" log from LoggableService!!!");
    }
}
