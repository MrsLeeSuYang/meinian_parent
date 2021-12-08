package com.cxy.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.cxy.dao.AddressDao;
import com.cxy.entity.PageResult;
import com.cxy.entity.QueryPageBean;
import com.cxy.pojo.Address;
import com.cxy.service.AddressService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service(interfaceClass = AddressService.class)
@Transactional
public class AddressServiceImpl implements AddressService {

    @Autowired
    AddressDao addressDao;

    @Override
    public void deleteById(Integer id) {
        addressDao.deleteById(id);
    }

    @Override
    public void add(Address address) {
        addressDao.add(address);
    }

    @Override
    public PageResult findPage(QueryPageBean queryPageBean) {

        PageHelper.startPage(queryPageBean.getCurrentPage(), queryPageBean.getPageSize());

        Page page = addressDao.findPage(queryPageBean.getQueryString());

        PageResult pageResult = new PageResult(page.getTotal(), page.getResult());

        return pageResult;
    }

    @Override
    public List<Address> findAllMaps() {
        return addressDao.findAllMaps();
    }
}
