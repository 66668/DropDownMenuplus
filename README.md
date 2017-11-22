筛选器 加强版
====
github上多个版本DropDownMenu的综合，补充了缺少的效果，修改了部分bug，使用集成更方便，具体源码已经注释，修改更快捷。
注：该demo没有实现一个条件的多选样式，需要自己实现

所有的样式都在DropMenuAdapter的getView()中，小伙伴们可以自己切换运行，查看效果。

(1)省市区三级筛选的效果图：
(第一级的不限的样式可以在第二级以后就可以设置不显示，这个坑小伙伴们自己实现吧，代码中已经标注)

<img width="450" height="800" src="https://github.com/66668/DropDownMenuplus/blob/master/gif/threechoose.gif"></img>

(2)五个条件+单选样式的多条件筛选效果图两个：
（可以根据该处代码设置N个条件的筛选）

<img width="450" height="800" src="https://github.com/66668/DropDownMenuplus/blob/master/gif/fivefilter01.gif"></img>

<img width="450" height="800" src="https://github.com/66668/DropDownMenuplus/blob/master/gif/singlechoose.gif"></img>

(3)单个gridView 和单个ListView效果图：
 (这是前辈大神的源码，小做修改就可以使用)

<img width="450" height="800" src="https://github.com/66668/DropDownMenuplus/blob/master/gif/singlelistgrid.gif"></img>

(4)两个gridView效果图：（这是前辈大神的demo,两个gridView的样式可以类推到N个GridView的样式，就如同我的第二个gif图，就是参考该处实现的。
不过这里有bug,复用item的时候，选中状态会混乱，这里的一个解决思路是用数据源的数据保存item状态，虽然对数据不友好，但是考虑的代码少，简单高效，请参考第二个gif源码实现）

<img width="450" height="800" src="https://github.com/66668/DropDownMenuplus/blob/master/gif/twogridchoose.gif"></img>

(5)省市等两级联动效果图：
（这也是前辈大神的源码，没有改动，该处实现效果很好，没有发现bug，可以直接使用）

<img width="450" height="800" src="https://github.com/66668/DropDownMenuplus/blob/master/gif/twolist.gif"></img>

欢迎star fork
