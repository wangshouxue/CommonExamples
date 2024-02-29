package com.example.mynetdemo
/**
 * @Description: 类作用描述
 * {"ret":0,"text":"","data":{"bottom_text":"Copyright © 2003-2023 0559e. All Rights Reserved.<br><a href=\"https://beian.miit.gov.cn/\">皖ICP备10203239号-4A</a>"}}
 */
data class AboutUsBean(var text:String?, var ret:Int?,var data:DataEntity?)
data class DataEntity(var bottom_text:String?)
