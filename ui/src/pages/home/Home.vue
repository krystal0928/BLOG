<template>
  <div class="wapper">
    <div class="left-card">
      <header class="list-header card" style="display:;">
        <nav role="navigation" class="list-nav">
          <ul class="nav-list left">
            <li class="nav-item active">
              <a href="/" >推荐</a>
            </li>
            <li class="nav-item">
              <a href="/?sort=newest" >最新</a>
            </li>
            <li class="nav-item" >
              <a href="/?sort=three_days_hottest" >热榜</a>
            </li>
          </ul> 
          <div class="dorp-down-area"></div>
        </nav>
      </header>
      <div class="article-card card">
        <div class="entry-list-wrap">
          <div name="entry-list" tag="div" class="entry-list list">
            <li data-growing-title="entryList" class="item">
              <div class="entry" style="margin-bottom: 0px;">
                <div data-v-78403b6b="" class="meta-container">
                  <a href="/user/43636194286093" target="_blank" rel="" class="user-message">
                    <div class="popover-box user-popover">xuyisheng</div>
                  </a>
                  <div class="date">11天前</div> 
                  <div class="tag_list">
                    <a href="/tag/Flutter" target="_blank" rel="" class="tag">Flutter</a>
                  </div>
                </div>
                <div class="content-wrapper" style="border-bottom: 1px solid rgba(228, 230, 235, 0.5);">
                  <div class="content-main">
                    <div class="title-row">
                      <a href="/post/7088864824284676110" target="_blank" rel="" title="为了看Flutter到底有没有人用我竟然" class="title">为了看Flutter到底有没有人用我竟然</a>
                      </div> 
                    <div class="abstract">
                      <a data-v-78403b6b="" href="/post/7088864824284676110" target="_blank" rel="">
                        <div data-v-78403b6b="">Flutter这个东西出来这么久了，到底市场占有率怎么样呢？为了让大家了解这一真实数据，也为了让大家了解当前Flutter在各大App中的使用情况，我今天下载了几百个App，占了手机将近80G空间，就</div>
                      </a>
                      </div>
                    <ul class="action-list jh-timeline-action-area">
                      <li class="item view">
                        <i></i> 
                        <span>5193</span>
                      </li>
                      <li class="item like">
                        <i data-v-78403b6b=""></i>
                        <span data-v-78403b6b="">44</span>
                      </li>
                      <li class="item comment">
                        <i data-v-78403b6b=""></i> 
                        <span data-v-78403b6b="">10</span>
                      </li>
                    </ul>
                  </div>
                  <img data-v-0808752c="" data-v-78403b6b="" src="https://p6-juejin.byteimg.com/tos-cn-i-k3u1fbpfcp/29092e57c0aa49be99cfdc7c8b3f5ae8~tplv-k3u1fbpfcp-no-mark:240:240:240:160.awebp?" alt="程序员全职接单一个月的感触" class="lazy thumb" data-src="https://p6-juejin.byteimg.com/tos-cn-i-k3u1fbpfcp/29092e57c0aa49be99cfdc7c8b3f5ae8~tplv-k3u1fbpfcp-no-mark:240:240:240:160.awebp?" loading="lazy" style="">
                </div>
              </div>
            </li>
            <li v-for="article in articleList" data-growing-title="entryList" class="item">
              <div class="entry" style="margin-bottom: 0px;">
                <div data-v-78403b6b="" class="meta-container">
                  <a href="/user/43636194286093" target="_blank" rel="" class="user-message">
                    <div class="popover-box user-popover">{{article.username}}</div>
                  </a>
                  <div class="date">{{article.date}}</div> 
                  <div class="tag_list">
                    <a href="/tag/Flutter" target="_blank" rel="" class="tag">Flutter</a>
                  </div>
                </div>
                <div class="content-wrapper" style="border-bottom: 1px solid rgba(228, 230, 235, 0.5);">
                  <div class="content-main">
                    <div class="title-row">
                      <a href="/post/7088864824284676110" target="_blank" rel="" :title="article.title" class="title">{{article.title}}</a>
                      </div> 
                    <div class="abstract">
                      <a data-v-78403b6b="" href="/post/7088864824284676110" target="_blank" rel="">
                        <div data-v-78403b6b="">{{article.description}}</div>
                      </a>
                      </div>
                    <ul class="action-list jh-timeline-action-area">
                      <li class="item view">
                        <i></i> 
                        <span>{{article.view}}</span>
                      </li>
                      <li class="item like">
                        <i data-v-78403b6b=""></i>
                        <span data-v-78403b6b="">{{article.like}}</span>
                      </li>
                      <li class="item comment">
                        <i data-v-78403b6b=""></i> 
                        <span data-v-78403b6b="">{{article.comment}}</span>
                      </li>
                    </ul>
                  </div>
                  <img data-v-0808752c="" data-v-78403b6b="" src="https://p6-juejin.byteimg.com/tos-cn-i-k3u1fbpfcp/29092e57c0aa49be99cfdc7c8b3f5ae8~tplv-k3u1fbpfcp-no-mark:240:240:240:160.awebp?" alt="程序员全职接单一个月的感触" class="lazy thumb" data-src="https://p6-juejin.byteimg.com/tos-cn-i-k3u1fbpfcp/29092e57c0aa49be99cfdc7c8b3f5ae8~tplv-k3u1fbpfcp-no-mark:240:240:240:160.awebp?" loading="lazy" style="">
                </div>
              </div>
            </li>
          </div>
        </div>
      </div>
    </div>

    <div class="right-card">
      <el-card class="box-card">
        <div class="signin-tip signin" data-v-68373e0a="" data-v-08a3e345=""><div class="first-line" style="opacity: 1;" data-v-68373e0a=""><div class="icon-text" data-v-68373e0a=""><svg width="24" height="24" viewBox="0 0 24 24" fill="none" xmlns="http://www.w3.org/2000/svg" class="icon" data-v-68373e0a=""><path fill-rule="evenodd" clip-rule="evenodd" d="M8 2C8 1.72386 7.77614 1.5 7.5 1.5H6.5C6.22386 1.5 6 1.72386 6 2L5.9995 3H3C2.44772 3 2 3.47259 2 4.05556V7H22V4.05556C22 3.47259 21.5523 3 21 3H18V2C18 1.72386 17.7761 1.5 17.5 1.5H16.5C16.2239 1.5 16 1.72386 16 2V3H8V2ZM22 8.5H2V20.9444C2 21.5274 2.44772 22 3 22H21C21.5523 22 22 21.5274 22 20.9444V8.5Z" fill="#FFCF8B" data-v-68373e0a=""></path><rect x="5" y="12" width="3" height="2" rx="1" fill="#FF7D00" data-v-68373e0a=""></rect><rect x="10.5" y="12" width="3" height="2" rx="1" fill="#FF7D00" data-v-68373e0a=""></rect><rect x="5" y="16" width="3" height="2" rx="1" fill="#FF7D00" data-v-68373e0a=""></rect><rect x="10.5" y="16" width="3" height="2" rx="1" fill="#FF7D00" data-v-68373e0a=""></rect><rect x="16" y="12" width="3" height="2" rx="1" fill="#FF7D00" data-v-68373e0a=""></rect><rect x="16" y="16" width="3" height="2" rx="1" fill="#FF7D00" data-v-68373e0a=""></rect></svg> <span class="title" data-v-68373e0a="">晚上好！</span></div> <button class="btn signin-btn" data-v-68373e0a=""><span class="btn-text" data-v-68373e0a="">去签到</span></button></div> <div class="second-line" style="opacity: 1;" data-v-68373e0a="">点亮你在社区的每一天</div><div class="byte-loading" style="display: none;"><div class="byte-loading__spinner"><i class="circular byte-icon byte-icon--loading"><svg t="1553157954893" class="icon" viewBox="0 0 1024 1024" version="1.1" xmlns="http://www.w3.org/2000/svg" p-id="2605" xmlns:xlink="http://www.w3.org/1999/xlink"><path d="M980.752 313.697c-25.789-60.972-62.702-115.725-109.713-162.736-47.012-47.011-101.764-83.924-162.736-109.713C645.161 14.542 578.106 1 509 1c-2.242 0-4.48 0.015-6.715 0.043-16.567 0.211-29.826 13.812-29.615 30.38 0.209 16.438 13.599 29.618 29.99 29.618l0.39-0.002c1.98-0.026 3.963-0.039 5.95-0.039 61.033 0 120.224 11.947 175.93 35.508 53.82 22.764 102.162 55.359 143.683 96.879s74.115 89.862 96.88 143.683C949.054 392.776 961 451.967 961 513c0 16.568 13.432 30 30 30s30-13.432 30-30c0-69.106-13.541-136.162-40.248-199.303z" p-id="2606"></path></svg></i><!----></div></div></div>
      </el-card>
    </div>
  </div>
</template>


<script lang="ts" setup>
import { ref } from 'vue'
const articleList = ref([
  {
    id: 1,
    username: '邹',
    date: '1天前',
    title: '为了看Flutter到底有没有人用我竟然222',
    description: 'sdfasdffsd',
    view: 11213,
    like: 123,
    comment: 123,
    cover: 'https://p6-juejin.byteimg.com/tos-cn-i-k3u1fbpfcp/29092e57c0aa49be99cfdc7c8b3f5ae8~tplv-k3u1fbpfcp-no-mark:240:240:240:160.awebp?'
  }
])
</script>

<style scoped>
.wapper {
  margin: 0 200px; 
  display: flex;
  flex-wrap: nowrap;
  justify-content: space-between;
}
.left-card {
  flex: 3;
  margin-right: 20px;
}
.list-header {
  padding: 1.3rem 1rem;
  border-bottom: 1px solid hsla(0,0%,59.2%,.1);
  margin-bottom: 10px;
}
.list-nav[data-v-11641108] {
  justify-content: flex-start!important;
}
.list-header .list-nav, .list-header .nav-list {
  display: flex;
  justify-content: space-between;
}
.nav-list {
  align-items: center;
  line-height: 1;
  display: flex;
  justify-content: space-between;
}
.nav-item {
  position: relative;
  cursor: pointer;
  padding: 0 1.2rem;
  font-size: 1.16rem;
  border-right: 1px solid hsla(0,0%,59.2%,.2);
}
.entry {
  cursor: pointer;
  position: relative;
  background: #fff;
  display: flex;
  flex-direction: column;
  align-items: flex-start;
}
.meta-container {
  color: #86909c;
}
.meta-container, .meta-row {
  display: flex;
  align-items: center;
}
.meta-container .user-message {
  display: flex;
  align-items: center;
  margin-right: 8px;
  max-width: 162px;
  font-size: 13px;
  line-height: 22px;
  color: #4e5969;
  white-space: nowrap;
  overflow: hidden;
  text-overflow: ellipsis;
  word-break: break-all;
}
.date:before {
  left: 0;
  position: absolute;
  top: 50%;
  transform: translateY(-50%);
  display: block;
  width: 1px;
  height: 14px;
  background: #e5e6eb;
  content: " ";
}
.meta-container .date {
  position: relative;
  padding: 0 10px;
  line-height: 22px;
  font-size: 13px;
  flex-shrink: 0;
}
.date:after, .date:before {
  right: 0;
  position: absolute;
  top: 50%;
  transform: translateY(-50%);
  display: block;
  width: 1px;
  height: 14px;
  background: #e5e6eb;
  content: " ";
}
.tag_lis {
  display: flex;
  align-items: center;
}
.tag_list .tag {
  position: relative;
  flex-shrink: 0;
  font-size: 13px;
  line-height: 22px;
  padding: 0 8px;
  color: #86909c;
}
.content-wrapper {
  display: flex;
  padding-bottom: 12px;
  border-bottom: 1px solid #e5e6eb;
  margin-top: 10px;
  width: 100%;
}
.content-wrapper .content-main {
  flex: 1 1 auto;
}
.title-row {
  display: flex;
  margin-bottom: 8px;
}
.title {
  font-weight: 700;
  font-size: 16px;
  line-height: 24px;
  color: #1d2129;
  width: 100%;
  display: -webkit-box;
  overflow: hidden;
  text-overflow: ellipsis;
  -webkit-box-orient: vertical;
  -webkit-line-clamp: 1;
}
.abstract {
  margin-bottom: 10px;
}
.abstract a[data-v-78403b6b] {
  color: #86909c;
  font-size: 13px;
  line-height: 22px;
  display: -webkit-box;
  overflow: hidden;
  text-overflow: ellipsis;
  -webkit-box-orient: vertical;
  -webkit-line-clamp: 1;
}
.action-list, .action-list>.item {
  display: flex;
  align-items: center;
}
.action-list>.item {
  position: relative;
  margin-right: 20px;
  font-size: 13px;
  line-height: 20px;
  color: #4e5969;
  flex-shrink: 0;
}
.action-list>.item {
  position: relative;
  margin-right: 20px;
  font-size: 13px;
  line-height: 20px;
  color: #4e5969;
  flex-shrink: 0;
}
.action-list>.item {
  position: relative;
  margin-right: 20px;
  font-size: 13px;
  line-height: 20px;
  color: #4e5969;
  flex-shrink: 0;
}
.action-list>.item.view i {
  display: block;
  width: 16px;
  height: 16px;
  background-size: 100%;
  background-image: url(//lf3-cdn-tos.bytescm.com/obj/static/xitu_juejin_web/img/view.1eda8fa.png);
}
.action-list>.item.like i {
  display: block;
  width: 16px;
  height: 16px;
  background-size: 100%;
  background-image: url(data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAACAAAAAgCAYAAABzenr0AAAACXBIWXMAABYlAAAWJQFJUiTwAAAAAXNSR0IArs4c6QAAAARnQU1BAACxjwv8YQUAAAJ9SURBVHgB7VZNbtNQEP7GP7AkN8DcoJyA5gRNTwCR2kqsUm9YEKEaoZRl0hUSBLWcAHOCpjdIT1AfIewgTjzM+AccxwHXLRYS/STnvbyxZ743b34e8L+DcEMMhu+fERk7DLTAuAwRjjz3eVD1+xsROD75eARmr7AczDlsVyVhoCbeDt85mXHmqLtkbst0Ko9jk31aVU9tAgvYW7FxYNJ3D85eufuTOZtKYiZu3X4z/LBdRU9tAia4E0+YL7I1z+3OmPEpkWOrip7aBED0RAcb7K8KeBb/alD+LQIa+TI48kxfuAfTVSlVMlybgAafpN1RYgujopwIOzpGEhuogGuloTc8bd2j5TmS8/VfHu7t5uUaeCaRygORPaqi00JFJAVn2UuNa667xXfEeJZ+rePR+Kool7gIQg67+RpBJTvo5QMoZHPXxFLW8Tld2lhoUqMOfg/JlKjdT2Pnpwf0bDlx3worG1EnPlENF0m5OayO5+7NyjRLHXgMfCsNQhumrBviRfRAhnqyu0KAYXmpcX/BfKKekL+dvBIheOUddkuNK7QO6A43ycXDvolYr5OtGTnlD3VU41rVZLdfccvIipMYDdYINAHZZJyicgR+4wQ0hbVH6Px7RBeNE9BM0lGbVxorTRNImhcxf8mvNxcDafOywBM0TUCqqEa/I09QbF6NECDQdjKuN6hGCJSlX2MENqXfGgFKS6hlmA9wi7hvLJ/qWEy/DL96AfMlEXWkU/mD0XiCkjud7kRk56iOltwRYz3ShM7KXlhpx4PheBR3qxzS6zbSi0YtyOZe9919D38ioNC2vIDl6NyQC2bWtzWVomvf93gWwg7KXH+HfwY/AGsn+Lf3Dim6AAAAAElFTkSuQmCC);
}
.action-list>.item.comment  i {
  display: block;
  width: 16px;
  height: 16px;
  background-size: 100%;
  background-image: url(data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAACAAAAAgCAYAAABzenr0AAAACXBIWXMAABYlAAAWJQFJUiTwAAAAAXNSR0IArs4c6QAAAARnQU1BAACxjwv8YQUAAAKRSURBVHgB7VZNbtpQEJ55BlR15RvUOUHTG5ATJD1BQ9NU6gq8JKiKoyrJ0u2qUmkFOUGaE5TegJwg7gnqLRh7MvMwwiDbYHCUDZ9kHph5M9+bN38AO+zwzEAoiEv3xz4v/KAFiK/0S6J/qPCBoui+Y38cFlC3HgHH7Zk1FTXZUIt/mivEPVY6GFFw4difPNiWwNW3X+dLhj1iA3JqProXq7EQ8TVoz4A1147OWfPkAjYhcO1+twirt7FSEexPiG4+26cDyMEXt1s3AI7Z+LsZ4TEFB1newBzjf2B6Gi8kaqwyvEpHFgmVtjmxcTgm401R44I2G0M2KjpEV5W9KbG0koC+8znrt47d8GFDCAk+gJCQwNyvwqS1LLNwBbHbHuQ7G99bJ4rXgY4LRPGqz4T2koda8EAERl2zIrory7hArlBnDmdSDSbHyf8WrwDVNHKV0YOyQdGNXhAPMwlgnHKjaHQPJUNBOEjamL9fhI7SMt0/Q3uu08wjoJGWLk+FZQKefLyEwIKSETcxwTCbANFfWQLAIygZCFifrjkEQq73WgixCaUziHWi+p1JIC65Hj/mpdt1oCQkq2u7+f4uk4BAGo8minguFQy2hFRXvlonqTuXgK5aRLqHc/m8vXK7LdgCE6hYskolTGtqqWnYsU+dmITJrnCvv/7sOXKSDVCBiScrJgeVBHInIokDuYqEsB5KQqgMl7uk1A4DwrpCkFLLExL0x1HAWfXCr2H4X2TOWh+wEAGB7pBQcRITzgwymvmxEilcVtp+cX1cfs20Drv2VKyDSVUPI4Ij3lRPEfFJclzXEvIQlXhioZ4QYaNjn/Q3IrAMiQmDA0wB+QGEflr/ENK6xXOXFS8QRQdFx/YdnhyP1D0hcwr1KvEAAAAASUVORK5CYII=);  }
.thumb {
  flex: 0 0 auto;
  width: 120px;
  height: 80px;
  margin-left: 24px;
  background-color: #fff;
  border-radius: 2px;
}
.right-card {
  flex: 1;
}
.card {
  border-radius: 4px;
  border: 1px solid #e4e7ed;
  background-color: #fff;
  box-shadow: 0px 0px 12px rgba(0, 0, 0, 0.12);
  padding: 15px;
}
.article-card {
  display: flex;
  overflow: hidden;
}
</style>