package co.com.grafana_serenity.automation.utils.Listener;

import net.thucydides.model.domain.DataTable;
import net.thucydides.model.domain.Story;
import net.thucydides.model.domain.TestOutcome;
import net.thucydides.model.domain.TestResult;
import net.thucydides.model.domain.TestTag; // Importante: Asegúrate de tener este import
import net.thucydides.model.screenshots.ScreenshotAndHtmlSource;
import net.thucydides.model.steps.ExecutedStepDescription;
import net.thucydides.model.steps.StepFailure;
import net.thucydides.model.steps.StepListener;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class InfluxDBListener implements StepListener {
    public InfluxDBListener() {
        System.out.println(">>> [SISTEMA] LISTENER INSTANCIADO CORRECTAMENTE");
    }

    @Override
    public void testFinished(TestOutcome result) {
        // 1. Limpiamos el nombre del escenario (Quitamos espacios y puntos)
        String scenarioName = result.getName().replaceAll("[^a-zA-Z0-9]", "_");
        String status = result.getResult().toString();
        long duration = result.getDuration();

        // 2. Extraemos los tags y los limpiamos (Solo letras, números y el @)
        String cleanTags = result.getTags().stream()
                .map(TestTag::getName)
                .map(t -> t.replaceAll("[^a-zA-Z0-9@]", "_"))
                .collect(Collectors.joining("-")); // Usamos guion para no confundir a Influx con la coma

        if (cleanTags.isEmpty()) cleanTags = "no_tag";

        System.out.println(">>> [DEBUG] ENVIANDO: " + scenarioName + " | TAGS: " + cleanTags);

        // FORMULACIÓN CORRECTA (Sin espacios en los Tags de Influx)
        // Estructura: tabla,tag1=valor,tag2=valor campo=valor
        String payload = String.format("test_results,scenario=%s,tag=%s status=\"%s\",value=1,duration=%d",
                scenarioName, cleanTags, status, duration);

        enviarAInflux(payload);
    }

    @Override
    public void testFinished(TestOutcome testOutcome, boolean b, ZonedDateTime zonedDateTime) {
        this.testFinished(testOutcome);
    }

    private void enviarAInflux(String data) {
        try {
            String influxUrl = System.getenv("INFLUX_URL");
            String influxToken = System.getenv("INFLUX_TOKEN");
            String influxBucket = System.getenv("INFLUX_BUCKET");
            String influxOrg = System.getenv("INFLUX_ORG");

            if (influxUrl == null) return;

            HttpClient client = HttpClient.newBuilder()
                    .connectTimeout(Duration.ofSeconds(10))
                    .build();

            String finalUrl = String.format("%s/api/v2/write?org=%s&bucket=%s&precision=s",
                    influxUrl, influxOrg, influxBucket);

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(finalUrl))
                    .header("Authorization", "Token " + influxToken)
                    .header("Content-Type", "text/plain; charset=utf-8")
                    .POST(HttpRequest.BodyPublishers.ofString(data))
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            // Log para saber si funcionó
            System.out.println(">>> [INFLUX] Status: " + response.statusCode());
            if (response.statusCode() != 204) {
                System.out.println(">>> [ERROR INFO] " + response.body());
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // --- MÉTODOS OBLIGATORIOS (SIN CAMBIOS) ---
    @Override public void testSuiteStarted(Class<?> aClass) {}
    @Override public void testSuiteStarted(Story story) {}
    @Override public void testSuiteFinished() {}
    @Override public void testStarted(String s) {}
    @Override public void testStarted(String s, String s1) {}
    @Override public void testStarted(String s, String s1, ZonedDateTime zonedDateTime) {}
    @Override public void testRetried() {}
    @Override public void stepStarted(ExecutedStepDescription esd) {}
    @Override public void skippedStepStarted(ExecutedStepDescription esd) {}
    @Override public void stepFailed(StepFailure sf) {}
    @Override public void stepFailed(StepFailure sf, List<ScreenshotAndHtmlSource> l, boolean b, ZonedDateTime z) {}
    @Override public void lastStepFailed(StepFailure sf) {}
    @Override public void stepIgnored() {}
    @Override public void stepPending() {}
    @Override public void stepPending(String s) {}
    @Override public void stepFinished() {}
    @Override public void stepFinished(List<ScreenshotAndHtmlSource> l, ZonedDateTime z) {}
    @Override public void testFailed(TestOutcome to, Throwable t) {}
    @Override public void testIgnored() {}
    @Override public void testSkipped() {}
    @Override public void testPending() {}
    @Override public void testIsManual() {}
    @Override public void notifyScreenChange() {}
    @Override public void useExamplesFrom(DataTable dt) {}
    @Override public void addNewExamplesFrom(DataTable dt) {}
    @Override public void exampleStarted(Map<String, String> m) {}
    @Override public void exampleFinished() {}
    @Override public void assumptionViolated(String s) {}
    @Override public void testRunFinished() {}
    @Override public void takeScreenshots(List<ScreenshotAndHtmlSource> l) {}
    @Override public void takeScreenshots(TestResult tr, List<ScreenshotAndHtmlSource> l) {}
}