package fr.dammerey.seichampsvb.util

import android.content.Context
import com.google.gson.Gson
import fr.dammerey.seichampsvb.data.AppConfig
import java.io.File
import java.io.FileOutputStream
import java.io.FileReader
import java.io.FileWriter
import java.io.InputStreamReader

object ConfigManager {
    lateinit var config : AppConfig
    private const val CONFIG_FILE = "config.json"
    private lateinit var appContext: Context
    private val gson = Gson()

    fun loadConfig(context: Context) {
        val ouvertureFichier = context.assets.open(CONFIG_FILE) // Gson ne peut pas accéder directement aux fichiers dans asset
        val lectureFichier = InputStreamReader(ouvertureFichier)
        config = Gson().fromJson(lectureFichier, AppConfig::class.java)
        lectureFichier.close()
    }

    fun init(context: Context) {
        appContext = context.applicationContext
        val file = File(appContext.filesDir, CONFIG_FILE)

        //si le fichier n'existe pas dans fileDir , on le copie depuis assets
        if (!file.exists()) {
            copyAssetToInternalStorage(appContext, CONFIG_FILE)
        }
        //charge le fichier modifiable
        val reader = FileReader(file)
        config = gson.fromJson(reader, AppConfig::class.java)
        reader.close()
        AppParams.updateFromConfig(config)

    }

    fun copyAssetToInternalStorage(context: Context, fileName: String) {
        val assetInput = context.assets.open(fileName)
        val outputFile = File(context.filesDir, fileName)
        assetInput.use { input ->
            FileOutputStream(outputFile).use { output ->
                input.copyTo(output)
            }
        }
    }

    fun saveConfig(nouvelleConfig: AppConfig) {
        val file = File(appContext.filesDir, CONFIG_FILE)
        val writer = FileWriter(file)
        gson.toJson(nouvelleConfig, writer)
        writer.close()

    }

}