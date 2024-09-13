package com.example.laara.internet

import kotlinx.serialization.Serializable
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST


/*
* [
  {
    "CollegeId": "22FE1A6129",
    "Name": "anand",
    "Password": "22FE1A6129",
    "MailId": "ramanaidu2004@gmail.com",
    "MobileNumber": "9182377052",
    "UserStatus": "User Found with Correct Credentials",
    "Regulation": "r20",
    "BranchName": "AIML",
    "Batch": "22"
  }
]*/

@Serializable
data class loginClass(
    val CollegeId:String="22FE1A1234",
    val Name:String= "",
    val UserStatus:String= "",
    val Regulation:String= "",
    val BranchName:String="",
    val Batch:String="",
    val MailId:String = "not available",
    val MobileNumber:String = "not available"

)
/*
*  {
    "SubjectCode": "R201201",
    "SubjectName": "MATHEMATICS-II",
    "shortsubname": ""
  },
*/

@Serializable
data class subList(
    val SubjectCode:String,
    val SubjectName:String
)

const val BASE_URL:String = "http://vignanslara.live"

private val laraRetrofit = Retrofit.Builder()
    .addConverterFactory(GsonConverterFactory.create())
    .baseUrl(BASE_URL)
    .build()

interface laraServiceApi{
    @FormUrlEncoded
    @POST("lara/result/appphpfiles/StudentLogin.php")
    suspend fun CheckUser(@Field("CollegeId") username :String, @Field("Password") password:String, ):List<loginClass>

    @FormUrlEncoded
    @POST("lara/result/appphpfiles/GetSubjectList.php")
    suspend fun getSubjects(@Field("TableName") tableName:String):List<subList>

    @FormUrlEncoded
    @POST("lara/result/appphpfiles/GetResults.php")
    suspend fun getResults(@Field("TableName" ) tableName:String, @Field("CollegeId")collegeId:String):List<Map<String, Any>>
    
}

object loginService{
    val getServiceApi:laraServiceApi by lazy {
        laraRetrofit.create(laraServiceApi::class.java)
    }
}