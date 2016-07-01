package com.example.dell.zhihu.Model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by DELL on 2016/6/25.
 */
public class Latest implements Serializable{


    /**
     * date : 20160625
     * stories : [{"images":["http://pic3.zhimg.com/f1c9286f427173e7c87af61856c4e7ba.jpg"],"type":0,"id":8476253,"ga_prefix":"062517","title":"知乎好问题 · 哪些软件堪称「神器」却不为大众所知？"},{"images":["http://pic2.zhimg.com/5f65bc08eade3eb842f7963460e23021.jpg"],"type":0,"id":8488471,"ga_prefix":"062516","title":"他的镜头下不仅有唯美的情色片，还有一段复杂的香港历史"},{"images":["http://pic4.zhimg.com/783de8f06397f20641f4715811130253.jpg"],"type":0,"id":8479875,"ga_prefix":"062515","title":"央视曝光的毒跑道，是如何一步步「毒」到孩子们的？"},{"images":["http://pic2.zhimg.com/c154a238d44719245d7c44bce4f9dfa5.jpg"],"type":0,"id":8484019,"ga_prefix":"062514","title":"一个有些黑历史的冷知识：美国人曾经很依赖捕鲸业"},{"images":["http://pic4.zhimg.com/57ddd4b50966db75bc0b7152b427ed27.jpg"],"type":0,"id":8462815,"ga_prefix":"062513","title":"软萌甜、性转换和姐弟恋，说的都是它"},{"title":"大误 · 快乐的她，却有一头迷之忧郁的蓝发","ga_prefix":"062512","images":["http://pic3.zhimg.com/07c1284acbb9b671e51dfda32856fcce.jpg"],"multipic":true,"type":0,"id":8477631},{"images":["http://pic4.zhimg.com/3a2162c43fdc9b297d1b349715aa9133.jpg"],"type":0,"id":8485406,"ga_prefix":"062511","title":"想创业来一把大的，到这几个城市试试"},{"images":["http://pic3.zhimg.com/bade6280056a41de6d8de0f06048b886.jpg"],"type":0,"id":8486752,"ga_prefix":"062510","title":"「我对普通人类没兴趣，你们中有外星人的话再来找我」"},{"images":["http://pic1.zhimg.com/9a46f067741c6ee7a754be6d73f96ed0.jpg"],"type":0,"id":8483968,"ga_prefix":"062509","title":"我以为我很聪明，直到看了这三个实验"},{"title":"他们偏不爱北欧风，致力于把《我爱我家》里的老干部风搬回家","ga_prefix":"062508","images":["http://pic1.zhimg.com/297d3a3b4ac0ef8d3b9d4dbb64a2d8a4.jpg"],"multipic":true,"type":0,"id":8485345},{"images":["http://pic4.zhimg.com/a0f22c999d2c83ba2496cc2083f320bf.jpg"],"type":0,"id":8486641,"ga_prefix":"062507","title":"两个故事告诉你，政治危机这回事，在欧盟太常见了"},{"images":["http://pic3.zhimg.com/0ad0af3600fd5ae282319e7bf63ada86.jpg"],"type":0,"id":8486995,"ga_prefix":"062507","title":"带走近百人生命的龙卷风，是怎样突然出现的？"},{"title":"上个好大学 · 有时会觉得自己在人民公园上大学","ga_prefix":"062507","images":["http://pic1.zhimg.com/6c29c3f01ee444dc1d9e2b67e9ec7888.jpg"],"multipic":true,"type":0,"id":8486364},{"images":["http://pic4.zhimg.com/a0f22c999d2c83ba2496cc2083f320bf.jpg"],"type":0,"id":8487189,"ga_prefix":"062507","title":"读读日报 24 小时热门 TOP 5 · 脱欧的影响"},{"images":["http://pic3.zhimg.com/cef15d776912187f611ef4ad0fe9e5ca.jpg"],"type":0,"id":8482920,"ga_prefix":"062506","title":"瞎扯 · 如何正确地吐槽"}]
     * top_stories : [{"image":"http://pic3.zhimg.com/0fb2d2840d28e8696827d0528fea2e46.jpg","type":0,"id":8476253,"ga_prefix":"062517","title":"知乎好问题 · 哪些软件堪称「神器」却不为大众所知？"},{"image":"http://pic1.zhimg.com/9eb38dd55c4971b8ccd601b8475b229c.jpg","type":0,"id":8479875,"ga_prefix":"062515","title":"央视曝光的毒跑道，是如何一步步「毒」到孩子们的？"},{"image":"http://pic4.zhimg.com/8d5982c86f2e3ff4fc1b45276e5f3a4f.jpg","type":0,"id":8483968,"ga_prefix":"062509","title":"我以为我很聪明，直到看了这三个实验"},{"image":"http://pic3.zhimg.com/43c32470a996c5e55418276a2a9e076e.jpg","type":0,"id":8487189,"ga_prefix":"062507","title":"读读日报 24 小时热门 TOP 5 · 脱欧的影响"},{"image":"http://pic4.zhimg.com/8095a30b030918ab25fe876e9bdc86b3.jpg","type":0,"id":8486364,"ga_prefix":"062507","title":"上个好大学 · 有时会觉得自己在人民公园上大学"}]
     */

    private String date;

    private List<StoryBean> stories;

    private List<TopStoriesBean> top_stories;

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

    public List<TopStoriesBean> getTop_stories() {
        return top_stories;
    }

    public void setTop_stories(List<TopStoriesBean> top_stories) {
        this.top_stories = top_stories;
    }


    public  static class  TopStoriesBean implements Serializable{


        /**
         * image : http://pic1.zhimg.com/e2ca238c7ba51a56572e4cd03aec8c08.jpg
         * type : 0
         * id : 8489193
         * ga_prefix : 062607
         * title : 上个好大学 · 我在西藏读大学，天蓝景美网不好
         */

        private String image;
        private int type;
        private int id;
        private String title;

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        @Override

        public String toString() {
            return "TopStoriesBean{" +
                    "image='" + image + '\'' +
                    ", type=" + type +
                    ", id=" + id +
                    ", title='" + title + '\'' +
                    '}';
        }
    }

}



