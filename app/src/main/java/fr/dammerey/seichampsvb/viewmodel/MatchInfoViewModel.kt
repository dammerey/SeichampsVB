package fr.dammerey.seichampsvb.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import fr.dammerey.seichampsvb.data.MatchInfo
import fr.dammerey.seichampsvb.util.AppParams
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.jsoup.Jsoup
import java.security.SecureRandom
import java.security.cert.X509Certificate
import javax.net.ssl.SSLContext
import javax.net.ssl.TrustManager
import javax.net.ssl.X509TrustManager

class MatchInfoViewModel : ViewModel() {

    private val _matchs = MutableStateFlow<List<MatchInfo>>(emptyList())
    val matchs: StateFlow<List<MatchInfo>> = _matchs
    val url1 = AppParams.URL_EQUIPE1
    val url2 = AppParams.URL_EQUIPE2
    private var nomEquipe : String? = null
    private val _errorMessage = MutableSharedFlow<String?>()
    val errorMessage = _errorMessage

    fun loadMatchs(equipe: String? = null) {
        nomEquipe = equipe
        Log.d("Ecran", "MatchInfoViewModel - LoadMatchs - Equipe sélectionnée: ${equipe}")

        viewModelScope.launch {
            val result = withContext(Dispatchers.IO) {
                try {
                    val connexion = trustAllCertificatesConnection()
                    val doc = connexion.get()
                    val table3 = doc.select("table").getOrNull(2) ?: throw IllegalStateException("Table introuvable dans le document HTML")
                    val rows = table3.select("tbody tr").drop(1)

                    rows.mapNotNull { row ->
                        val tds = row.select("td").take(3)
                        if (tds.size == 3) {
                            MatchInfo(
                                position = tds[0].text(),
                                nomEquipe = tds[1].text(),
                                score = tds[2].text()
                            )
                        } else null
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                    _errorMessage.emit("Pas de reseau \nou site non disponible")
                    emptyList()
              }
            }
            _matchs.value = result
        }
    }

    private fun trustAllCertificatesConnection(): org.jsoup.Connection {
        val trustAllCerts = arrayOf<TrustManager>(object : X509TrustManager {
            override fun checkClientTrusted(chain: Array<X509Certificate>, authType: String) {}
            override fun checkServerTrusted(chain: Array<X509Certificate>, authType: String) {}
            override fun getAcceptedIssuers(): Array<X509Certificate> = arrayOf()
        })

        val sslContext = SSLContext.getInstance("TLS")
        sslContext.init(null, trustAllCerts, SecureRandom())
        val urlConnexion = when (nomEquipe) {
            AppParams.nom_EQUIPE1 -> url1
            AppParams.nom_EQUIPE2 -> url2
            else -> url1
        }
        return Jsoup.connect(urlConnexion).sslSocketFactory(sslContext.socketFactory)
    }
}