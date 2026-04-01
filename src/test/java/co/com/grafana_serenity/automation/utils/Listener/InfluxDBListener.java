package co.com.grafana_serenity.automation.utils.Listener;

import net.thucydides.model.domain.DataTable;
import net.thucydides.model.domain.Story;
import net.thucydides.model.domain.TestOutcome;
import net.thucydides.model.domain.TestResult;
import net.thucydides.model.domain.TestTag;
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
        System.out.println(">>> [DEBUG] TEST FINALIZADO: " + result.getName());

        // 1. Extraemos el nombre del escenario (limpiando espacios)
        String scenarioName = result.getName().replace(" ", "_");
        String status = result.getResult().toString();
        long duration = result.getDuration();

        // 2. Extraemos los Tags de Cucumber (@login, @smoke, etc.)
        String allTags = result.getTags().stream()
                .map(TestTag::getName)
                .collect(Collectors.joining(","));

        String tagValue = allTags.isEmpty() ? "no_tag" : allTags;

        System.out.println(">>> [DEBUG] FINALIZADO: " + scenarioName + " | ESTADO: " + status + " | TAGS: " + tagValue);

        // MÉTRICA 1: Tiempos de respuesta (Agregamos scenario y tag)
        String payloadDuration = String.format("login_metrics,scenario=%s,tag=%s status=\"%s\",duration=%d",
                scenarioName, tagValue, status, duration);

        // MÉTRICA 2: Conteo de ejecuciones (Agregamos scenario como TAG para que salga en la tabla)
        // El formato es: nombre_tabla,tag1=valor,tag2=valor campo=valor
        String payloadCount = String.format("test_results,scenario=%s,tag=%s status=\"%s\",value=1",
                scenarioName, tagValue, status);

        // Enviamos ambas métricas a la nube
        enviarAInflux(payloadDuration);
        enviarAInflux(payloadCount);
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

            if (influxUrl == null || influxToken == null) {
                System.out.println("[ERROR] Faltan variables de entorno para InfluxDB");
                return;
            }

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

            if (response.statusCode() >= 200 && response.statusCode() < 300) {
                System.out.println(">>> [EXITO] DATO ENVIADO A INFLUXDB CLOUD (" + response.statusCode() + ")");
            } else {
                System.out.println("[ERROR] InfluxDB Cloud rechazó los datos: " + response.body());
            }

        } catch (Exception e) {
            System.out.println("[ERROR] FALLÓ EL ENVÍO AL API: " + e.getMessage());
        }
    }

    // --- MÉTODOS OBLIGATORIOS RESTANTES (SIN CAMBIOS) ---
    @Override public void testSuiteStarted(Class<?> aClass) {}
    @Override public void testSuiteStarted(Story story) {}
    @Override public void testSuiteFinished() {}
    @Override public void testStarted(String s) {}
    @Override public void testStarted(String s, String s1) { System.out.println(">>> [DEBUG] LISTENER INICIADO"); }
    @Override public void testStarted(String s, String s1, ZonedDateTime zonedDateTime) { System.out.println(">>> [DEBUG] LISTENER INICIADO (4.x)"); }
    @Override public void testRetried() {}
    @Override public void stepStarted(ExecutedStepDescription executedStepDescription) {}
    @Override public void skippedStepStarted(ExecutedStepDescription executedStepDescription) {}
    @Override public void stepFailed(StepFailure stepFailure) {}
    @Override public void stepFailed(StepFailure stepFailure, List<ScreenshotAndHtmlSource> list, boolean b, ZonedDateTime zonedDateTime) {}
    @Override public void lastStepFailed(StepFailure stepFailure) {}
    @Override public void stepIgnored() {}
    @Override public void stepPending() {}
    @Override public void stepPending(String s) {}
    @Override public void stepFinished() {}
    @Override public void stepFinished(List<ScreenshotAndHtmlSource> list, ZonedDateTime zonedDateTime) {}
    @Override public void testFailed(TestOutcome testOutcome, Throwable throwable) {}
    @Override public void testIgnored() {}
    @Override public void testSkipped() {}
    @Override public void testPending() {}
    @Override public void testIsManual() {}
    @Override public void notifyScreenChange() {}
    @Override public void useExamplesFrom(DataTable dataTable) {}
    @Override public void addNewExamplesFrom(DataTable dataTable) {}
    @Override public void exampleStarted(Map<String, String> map) {}
    @Override public void exampleFinished() {}
    @Override public void assumptionViolated(String s) {}
    @Override public void testRunFinished() {}
    @Override public void takeScreenshots(List<ScreenshotAndHtmlSource> list) {}
    @Override public void takeScreenshots(TestResult testResult, List<ScreenshotAndHtmlSource> list) {}
}