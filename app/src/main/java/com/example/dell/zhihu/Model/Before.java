package com.example.dell.zhihu.Model;

import java.util.List;

/**
 * Created by DELL on 2016/6/28.
 */
public class Before {

    /**
     * date : 20160628
     * stories : [{"images":["http://pic1.zhimg.com/62fd0e314e02ec6abf38ca3f0d72c760.jpg"],"type":0,"id":8500490,"ga_prefix":"062821","title":"都是打打杀杀的机战，这部动画里却有细腻美好的青春"},{"images":["http://pic4.zhimg.com/8e74df521e97366d81027feefb7d7ccb.jpg"],"type":0,"id":8500182,"ga_prefix":"062820","title":"淘汰英格兰，「爆冷」的冰岛只有 30 万人，他们是怎么做到的？"},{"images":["http://pic4.zhimg.com/a9de604e9dfe14ef1e5ad84a61b6598f.jpg"],"type":0,"id":8493044,"ga_prefix":"062819","title":"假如孩子见了你就哭，不要急于下结论「孩子不喜欢我」"},{"images":["http://pic3.zhimg.com/c0e9d4c066abcf943c0557636ee7d87e.jpg"],"type":0,"id":8494403,"ga_prefix":"062818","title":"「加班都忙不完了，还是开袋薯片压压惊吧」"},{"images":["http://pic2.zhimg.com/a80509825ce3c21c870e1c067b8b4a69.jpg"],"type":0,"id":8480152,"ga_prefix":"062817","title":"知乎好问题 · 你有哪些内行人才知道的省钱诀窍？"},{"images":["http://pic3.zhimg.com/196254011eb0ed666f1bdbb42ebf92fa.jpg"],"type":0,"id":8499029,"ga_prefix":"062816","title":"「我穿这裤子看起来胖吗？」「不不，一点都不胖」"},{"images":["http://pic2.zhimg.com/f14758b8f340a7d8a31320683a575079.jpg"],"type":0,"id":8497082,"ga_prefix":"062815","title":"脸上痘有点多，「果酸换肤」靠谱吗？"},{"images":["http://pic1.zhimg.com/7d4f8d913d4e7c6d9d28d69ac58bcb68.jpg"],"type":0,"id":8498792,"ga_prefix":"062814","title":"恍然大悟，百科和搜索引擎这样用找资料更高效"},{"images":["http://pic3.zhimg.com/78b679c644e443466faeab51e72c09ba.jpg"],"type":0,"id":8498687,"ga_prefix":"062813","title":"细胞里发现的这条「蛇」，或许跟癌症还有关系"},{"images":["http://pic4.zhimg.com/1da2fec7c46b60d3c9dc2ad1771b5e37.jpg"],"type":0,"id":8498675,"ga_prefix":"062812","title":"大误 · 如果你们能看到，请务必把所有的信息看完"},{"images":["http://pic3.zhimg.com/ee648598625c9ed741985ddd522f519e.jpg"],"type":0,"id":8474066,"ga_prefix":"062811","title":"两个孩子的故事，让人感慨「可怜生在帝王家」"},{"images":["http://pic2.zhimg.com/e56db3b1429611ab26405da9d6133745.jpg"],"type":0,"id":8498068,"ga_prefix":"062810","title":"《小情歌》很难唱好，但还远没有到吴青峰的最高音"},{"images":["http://pic3.zhimg.com/e93715d00404c3651d85f032a6a65e4a.jpg"],"type":0,"id":8480639,"ga_prefix":"062809","title":"同样的钱，你会买奢侈品，还是环球旅行？"},{"images":["http://pic2.zhimg.com/142ea68e84fb50306cf9efabe5e633b5.jpg"],"type":0,"id":8496552,"ga_prefix":"062808","title":"你所在的行业有什么不为人知的趣事？"},{"images":["http://pic2.zhimg.com/f91add0f98c6d5c586d02c1ea88451b1.jpg"],"type":0,"id":8497171,"ga_prefix":"062807","title":"比起「读万卷书」，这个抓小蝴蝶的探险家好像更呆萌"},{"images":["http://pic3.zhimg.com/d8e9e140d86becf88e2971fd33e391be.jpg"],"type":0,"id":8480106,"ga_prefix":"062807","title":"我很有钱，凭什么不能任性地花给我支持的候选人？"},{"images":["http://pic2.zhimg.com/958bc4e8c5679160bcc61918f53034c9.jpg"],"type":0,"id":8496513,"ga_prefix":"062807","title":"上个好大学 · 律师和法官们读书的时候，他们在读什么"},{"images":["http://pic3.zhimg.com/50353110c16a58f5c68121460aaf498a.jpg"],"type":0,"id":8497509,"ga_prefix":"062807","title":"读读日报 24 小时热门 TOP 5 · 人机比赛三个月之后，李世乭谈了谈他的感受"},{"images":["http://pic3.zhimg.com/d3cfd8258d14bdd60fcdd7593a29eac6.jpg"],"type":0,"id":8497153,"ga_prefix":"062806","title":"瞎扯 · 如何正确地吐槽"},{"images":["http://pic1.zhimg.com/de0de27f15374ed1ebed34edd636fbe4.jpg"],"type":0,"id":8494696,"ga_prefix":"062806","title":"这里是广告 · 你听什么情歌，可能与依恋类型有关"}]
     */

    private String date;
    /**
     * images : ["http://pic1.zhimg.com/62fd0e314e02ec6abf38ca3f0d72c760.jpg"]
     * type : 0
     * id : 8500490
     * ga_prefix : 062821
     * title : 都是打打杀杀的机战，这部动画里却有细腻美好的青春
     */

    private List<StoryBean> stories;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public List<StoryBean> getStories() {
        return stories;
    }

    public void setStories(List<StoryBean> stories) {
        this.stories = stories;
    }


}
