package no.acntech.project101.company.consumer;

import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;

import no.acntech.project101.company.model.BrregRespons;

import java.net.URI;

@Component
public class BrregRestClient {

    private final WebClient webClient;

    private final String url = "https://webapi.no/api/v1/brreg/{orgnr}";

    public BrregRestClient(final WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.build();
    }

    public String lookupOrganizationName(final String organisasjonsnummer) {
        System.out.println();
        final var uri = getUri(organisasjonsnummer);

        final var brregRespons = webClient.get()
                .uri(uri)
                .retrieve()
                .bodyToMono(BrregRespons.class)
                .blockOptional();

        return brregRespons.map(respons -> respons.data().name())
                .orElse(null);
    }

    private URI getUri(String organisasjonsnummer) {
        return UriComponentsBuilder
                .fromUriString(url)
                .buildAndExpand(organisasjonsnummer)
                .toUri();
    }
}
