package org.ajou.realcoding.lolcrawler.lolcrawler.api;


import com.fasterxml.jackson.core.type.TypeReference;
import org.ajou.realcoding.lolcrawler.lolcrawler.domain.CurrentSummonerStat;
import org.ajou.realcoding.lolcrawler.lolcrawler.domain.EncryptedSummonerId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Set;

@Service
public class RiotDeveloperPortalApiClient {
    private final String apiKey = "RGAPI-a913199b-4db3-4b48-afaf-a6baab9ad656";
    private final String summonerV4Url = "https://kr.api.riotgames.com/lol/summoner/v4/summoners/by-name/{summonerName}?api_key={apiKey}";
    private final String leagueV4Url = "https://kr.api.riotgames.com/lol/league/v4/entries/by-summoner/{encryptedSummonerId}?api_key={apiKey}";

    @Autowired
    RestTemplate restTemplate;

    public EncryptedSummonerId requestEncryptedSummonerId(String summonerName){
        return restTemplate.exchange(summonerV4Url, HttpMethod.GET, null, EncryptedSummonerId.class, summonerName, apiKey)
                .getBody();
    }
    public Set<CurrentSummonerStat> requestCurrentSummonerStat(EncryptedSummonerId encryptedSummonerId){
        return restTemplate.exchange(leagueV4Url, HttpMethod.GET, null, new ParameterizedTypeReference<Set<CurrentSummonerStat>>(){}, encryptedSummonerId.getId(), apiKey)
                .getBody();
    }

}
