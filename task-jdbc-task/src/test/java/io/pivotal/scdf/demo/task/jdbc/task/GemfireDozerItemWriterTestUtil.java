/*
 * Copyright 2013-2014 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.pivotal.scdf.demo.task.jdbc.task;

import io.pivotal.scdf.demo.task.jdbc.task.common.GemfireDozerItemWriter;
import org.dozer.DozerBeanMapper;
import org.dozer.Mapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.*;


@Component
public class GemfireDozerItemWriterTestUtil {
    private static final Map<String, Map<String, Object>> DATA_SET = createDataSet();
    private static Logger LOG = LoggerFactory.getLogger(GemfireDozerItemWriterTestUtil.class);
    Map<String, Object> map = new HashMap<>();
    Exception ex = null;

    @Autowired
    ConfigurableApplicationContext applicationContext;

    @Autowired
    GemfireDozerItemWriter itemWriter;

    @Autowired
    DozerBeanMapper dmb;

    public Object testItemWriter(String regionName) {
        Object k = new Object();
        try {
            List<Map<String, Object>> listMap = new ArrayList<>();
            System.out.println("Region to Test : " + regionName);
            if (regionName != null) {
                map = DATA_SET.get(regionName);
            }
            Class K = Class.forName("io.pivotal.scdf.demo.task.common.key." + regionName+ "Key");
             k = K.newInstance();
            LOG.debug("KeyClass={}", K.getName());
            listMap.add(map);
            itemWriter.write(listMap);
            Mapper mp = dmb;
            LOG.info("Starting Dozer Mapping of Class [{}]", K.getName());
            k = mp.map(map, K);
        } catch (Exception e) {
            System.out.println("ex: " + e.getMessage());
            ex = e;
        }
        LOG.debug("Key returned in test={}",k.toString());
        return k;
    }


    public static Map<String, Map<String, Object>> createDataSet() {
        Map<String, Map<String, Object>> dataSet = new HashMap<>();
        dataSet.put("RoyaltySchedule", createRoyaltyScheduleMap());
        dataSet.put("Sale", createSalesMap());
        dataSet.put("SalesDetail", createSalesDetailMap());
        dataSet.put("Title", createTitlesMap());
        dataSet.put("TitleAuthor", createTitleAuthorsMap());
        dataSet.put("TitleEditor", createTitleEditorsMap());
        dataSet.put("Publisher", createPublisherMap());
        dataSet.put("Author", createAuthorsMap());

        return Collections.unmodifiableMap(dataSet);
    }

    public static Map<String, Object> createRoyaltyScheduleMap() {
        Map<String, Object> result = new HashMap<String, Object>();
        // output from log used failure log used for test data
        // {lorange=5001, hirange=50000, titleId=BU1032, royalty=0.12}
        result.put("lorange", 5001);
        result.put("hirange", 50000);
        result.put("titleId", "BU1032");
        result.put("royalty", 0.12);
        return result;
    }

    //working method change all others to this model
    public static Map<String, Object> createSalesMap() {
        Map<String, Object> salesItems = new HashMap<String, Object>();
        Date myDate = null;
        try {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            Calendar date = Calendar.getInstance();
            date.set(Calendar.YEAR, 1999);
            date.set(Calendar.MONTH, 7);
            date.set(Calendar.DAY_OF_MONTH, 26);
            myDate = date.getTime();
        } catch (Exception e) {
            LOG.error("Date of course of course");
        }
        salesItems.put("sonum", 21);
        salesItems.put("sdate", myDate);
        salesItems.put("ponum", "D4482");
        salesItems.put("storId", 8178);
        return salesItems;
    }

    public static Map<String, Object> createSalesDetailMap() {
        Map<String, Object> salesDetailsItems = new HashMap<String, Object>();
        // output from log used failure log used for test data
        // {sonum=2, sdate=1998-09-14, ponum=D4482, storId=7067}
        Date myDate = null;
        try {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            Calendar c = Calendar.getInstance();
            c.set(Calendar.YEAR, 1999);
            c.set(Calendar.MONTH, 7);
            c.set(Calendar.DAY_OF_MONTH, 27);
            myDate = c.getTime();
        } catch (Exception e) {
            LOG.error("Date of course of course");
        }
        salesDetailsItems.put("sonum", 2);
        salesDetailsItems.put("qtyOrdered", 10);
        salesDetailsItems.put("titleId", "PS2091");
        salesDetailsItems.put("qtyShipped", 10);
        salesDetailsItems.put("dateShipped", myDate);
        return salesDetailsItems;
    }

    public static Map<String, Object> createTitlesMap() {
        Map<String, Object> titlesMapItems = new HashMap<>();
        // output from log used failure log used for test data
        // {sonum=2, sdate=1998-09-14, ponum=D4482, storId=7067}
        Date myDate = null;
        try {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            Calendar c = Calendar.getInstance();
            c.set(Calendar.YEAR, 1989);
            c.set(Calendar.MONTH, 7);
            c.set(Calendar.DAY_OF_MONTH, 26);
            myDate = c.getTime();
        } catch (Exception e) {
            LOG.error("Date of course of course");
        }
        titlesMapItems.put("titleId", "PS7777");
        titlesMapItems.put("pubId", "0736");
        titlesMapItems.put("title", "Emotional Security: A New Algorithm");
        titlesMapItems.put("pubdate", myDate);
        titlesMapItems.put("price", 17.99);
        titlesMapItems.put("ytdSales", 3336);
        titlesMapItems.put("contract", 1);
        titlesMapItems.put("type", "psychology");
        titlesMapItems.put("note",
                "Protecting yourself and your loved ones from undue emotional stress in the modern world.  Use of computer and nutritional aids emphasized.");
        return titlesMapItems;
    }

    public static Map<String, Object> createTitleAuthorsMap() {
        Map<String, Object> titleAuthorItems = new HashMap<>();
        // {auOrd=1, auId=409-56-7008, titleId=BU1032, royaltyshare=0.60}
        titleAuthorItems.put("auId", "409-56-7008");
        titleAuthorItems.put("titleId", "BU1032");
        titleAuthorItems.put("auOrd", 1);
        titleAuthorItems.put("royaltyshare", 0.60);
        return titleAuthorItems;
    }

    public static Map<String, Object> createTitleEditorsMap() {
        Map<String, Object> titleEditorItems = new HashMap<>();
        titleEditorItems.put("edId", "826-11-9034");
        titleEditorItems.put("titleId", "BU20752");
        titleEditorItems.put("edOrd", 2);
        return titleEditorItems;
    }

    public static Map<String, Object> createPublisherMap() {
        Map<String, Object> publisherItems = new HashMap<>();
        publisherItems.put("pubId", "0736");
        publisherItems.put("pubName", "New Age Books");
        publisherItems.put("address", "1 1st St");
        publisherItems.put("city", "Washington");
        publisherItems.put("state", "DC");
        return publisherItems;
    }

    public static Map<String, Object> createAuthorsMap() {
        Map<String, Object> authorItems = new HashMap<>();
        authorItems.put("auId", "409-56-7008");
        authorItems.put("auLname", "Bennet");
        authorItems.put("auFname", "Abraham");
        authorItems.put("phone", "415 658-9932");
        authorItems.put("address", "6223 Bateman St.");
        authorItems.put("city", "Berkeley");
        authorItems.put("state", "CA");
        authorItems.put("zip", "94705");
        return authorItems;
    }
}
