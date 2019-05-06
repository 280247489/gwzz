package com.memory.solr;

import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * @Auther: cui.Memory
 * @Date: 2019/4/28 0028 9:13
 * @Description:
 */
public class SolrUtils {
    @Autowired
    private SolrClient solrClient;

    /**
     * Solr检索查询
     * @param collection    查询核心名称
     * @param searchKey     查询关键字
     * @param start         分页-页数
     * @param limit         分页-条数
     * @param sort          排序字段:"field1"
     * @param sortType      排序规则:asc||desc
     * @param hl            是否高亮显示匹配关键字:true||false
     * @param hlf           检索高亮显示字段:"field1, field2"
     * @param hlPre         高亮显示字段前缀:"<span style='color:red'>"
     * @param hlPost        高亮显示字段后缀:"</span>"
     * @throws IOException
     * @throws SolrServerException
     */
    public void solrSearch(Class entity, String collection, String searchKey, int start, int limit, String sort, String sortType,
                          boolean hl, String hlf, String hlPre, String hlPost) throws IOException, SolrServerException {
        if( (collection!=null && !"".equals(collection)) && (searchKey!=null && !"".equals(searchKey)) ){
            SolrQuery query = new SolrQuery();
            query.set("df", "solrSearchKey");
            query.setQuery(searchKey);
            query.set("wt","json");//设置返回格式
            query.setHighlight(hl);//设置是否高亮
            query.addHighlightField(hlf);//设置高亮字段
            query.setHighlightSimplePre(hlPre);
            query.setHighlightSimplePost(hlPost);
            if(sort!=null && !"".equals(sort)){
                //设置排序规则
                query.setSort(sort, "asc".equals(sortType)?SolrQuery.ORDER.asc: SolrQuery.ORDER.desc);
            }
            //设置分页
            if(start<=0)
                start = 1;
            if(limit<=0)
                limit = 10;
            query.set("start", (start - 1) * limit);
            query.set("rows", limit);
            //----------------------------------------------------------------
            QueryResponse queryResponse = solrClient.query(collection, query);//进行查询得到返回结果
            //取出查询结果
            SolrDocumentList results = queryResponse.getResults();
            //得到所获得的新闻条数
            long numFound = results.getNumFound();
            //取出高亮部分
            Map<String, Map<String, List<String>>> highlighting = queryResponse.getHighlighting();

            System.out.println("总条数:"+numFound);
            System.out.println("数据主体:\n"+results.toString());
            System.out.println("数据高亮:\n"+highlighting.toString());

        }

    }
    /**
     * 增加修改
     */
    public void solrAddorUpd(TestSolr testSolr){
        try {
            SolrInputDocument doc = new SolrInputDocument();
            doc.setField("id", testSolr.getId());
            doc.setField("test_title", testSolr.getTestTitle());
            doc.setField("test_info", testSolr.getTestInfo());
            doc.setField("test_content", testSolr.getTestContent());
            doc.setField("test_time", testSolr.getTestTime());
            solrClient.add(doc);
            solrClient.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    /**
     * 删除
     */
    public void solrDel(String id){
        try {
            solrClient.deleteById(id);
            solrClient.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
