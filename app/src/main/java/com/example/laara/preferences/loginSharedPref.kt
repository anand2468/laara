package com.example.laara.preferences

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.example.laara.internet.loginClass
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map


/* [
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
class loginSharedPref(private val context: Context) {
    companion object{
        val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "login_info")
        val COLLEGE_ID = stringPreferencesKey("college_id")
        val STATUS = booleanPreferencesKey("user_status")
        val MAIL_ID = stringPreferencesKey("mail_id")
        val MOBILE_NUMBER = stringPreferencesKey("mobile_number")
        val REGULATION = stringPreferencesKey("regulation")
        val BRANCH_NAME = stringPreferencesKey("branch_name")
        val BATCH = stringPreferencesKey("batch")
        val NAME = stringPreferencesKey("name")

    }

    val status: Flow<Boolean?> = context.dataStore.data
        .map {
            preferences ->
            preferences[STATUS]
        }


    val getCredentials: Flow<loginClass> = context.dataStore.data
        .map {
            preferences ->
            val collegeId = preferences[COLLEGE_ID]?:"22FE1A1234"
            val mailId = preferences[MAIL_ID]?:"example@gmail.com"
            val mobile = preferences[MOBILE_NUMBER]?: "9876543219"
            val name = preferences[NAME]?: "admin"
            loginClass(Name = name, CollegeId = collegeId, MailId = mailId, MobileNumber = mobile )
        }
    suspend fun setCredentials(data:loginClass){
        context.dataStore.edit { preferences ->
            preferences[COLLEGE_ID] = data.CollegeId
            preferences[STATUS] = true
            preferences[MAIL_ID] = data.MailId
            preferences[MOBILE_NUMBER] = data.MobileNumber
            preferences[REGULATION] = data.Regulation
            preferences[BRANCH_NAME] = data.BranchName
            preferences[BATCH] = data.Batch
            preferences[NAME] = data.Name
        }
    }

    suspend fun removeUserName(){
        context.dataStore.edit { it.clear() }
    }
}