package co.com.grafana_serenity.automation.utils.Listener;


import net.thucydides.model.domain.DataTable;
import net.thucydides.model.domain.Story;
import net.thucydides.model.domain.TestOutcome;
import net.thucydides.model.domain.TestResult;
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

public class InfluxDBListener implements StepListener {
    public InfluxDBListener() {
        System.out.println(">>> [SISTEMA] LISTENER INSTANCIADO CORRECTAMENTE");
    }

    @Override
    public void testFinished(TestOutcome result) {

        System.out.println(">>> [DEBUG] TEST FINALIZADO: " + result.getName());
        String testName = result.getName().replace(" ", "_");
        String status = result.getResult().toString();
        long duration = result.getDuration();

        System.out.println(">>> [DEBUG] FINALIZADO: " + testName + " | ESTADO: " + status);

        // MÉTRICA 1: Tiempos de respuesta
        String payloadDuration = String.format("login_metrics,test=%s status=\"%s\",duration=%d",
                testName, status, duration);

        // MÉTRICA 2: Conteo de ejecuciones (Para Dashboards de Total/Fallidos)
        String payloadCount = String.format("test_results,status=%s value=1", status);

        // Enviamos ambas métricas
        enviarAInflux(payloadDuration);
        enviarAInflux(payloadCount);


    }

    // --- EL CAMBIO CLAVE PARA SERENITY 4.x ---
    @Override
    public void testFinished(TestOutcome testOutcome, boolean b, ZonedDateTime zonedDateTime) {
        // Redirigimos la ejecución al método que tiene la lógica de envío
        this.testFinished(testOutcome);
    }


    private void enviarAInflux(String data) {
        try {
            HttpClient client = HttpClient.newBuilder()
                    .connectTimeout(Duration.ofSeconds(5))
                    .build();

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("http://localhost:8086/write?db=serenity_db"))
                    .POST(HttpRequest.BodyPublishers.ofString(data))
                    .build();

            client.send(request, HttpResponse.BodyHandlers.ofString());
            System.out.println(">>> [EXITO] DATO ENVIADO A INFLUXDB: " + data);
        } catch (Exception e) {
            System.out.println(">>> [ERROR] FALLÓ EL ENVÍO: " + e.getMessage());
        }
    }



    // --- MÉTODOS OBLIGATORIOS PARA COMPATIBILIDAD CON SERENITY 4.2.x ---

    @Override
    public void testSuiteStarted(Class<?> aClass) {

    }

    @Override
    public void testSuiteStarted(Story story) {

    }

    @Override
    public void testSuiteFinished() {

    }

    @Override
    public void testStarted(String s) {

    }

    @Override
    public void testStarted(String s, String s1) {
        System.out.println(">>> [DEBUG] EL LISTENER SE HA INICIADO CORRECTAMENTE");
    }

    @Override
    public void testStarted(String s, String s1, ZonedDateTime zonedDateTime) {
        System.out.println(">>> [DEBUG] EL LISTENER SE HA INICIADO CORRECTAMENTE (4.x)");

    }


    @Override
    public void testRetried() {

    }

    @Override
    public void stepStarted(ExecutedStepDescription executedStepDescription) {

    }

    @Override
    public void skippedStepStarted(ExecutedStepDescription executedStepDescription) {

    }

    @Override
    public void stepFailed(StepFailure stepFailure) {

    }

    @Override
    public void stepFailed(StepFailure stepFailure, List<ScreenshotAndHtmlSource> list, boolean b, ZonedDateTime zonedDateTime) {

    }

    @Override
    public void lastStepFailed(StepFailure stepFailure) {

    }

    @Override
    public void stepIgnored() {

    }

    @Override
    public void stepPending() {

    }

    @Override
    public void stepPending(String s) {

    }

    @Override
    public void stepFinished() {

    }

    @Override
    public void stepFinished(List<ScreenshotAndHtmlSource> list, ZonedDateTime zonedDateTime) {

    }

    @Override
    public void testFailed(TestOutcome testOutcome, Throwable throwable) {

    }

    @Override
    public void testIgnored() {

    }

    @Override
    public void testSkipped() {

    }

    @Override
    public void testPending() {

    }

    @Override
    public void testIsManual() {

    }

    @Override
    public void notifyScreenChange() {

    }

    @Override
    public void useExamplesFrom(DataTable dataTable) {

    }

    @Override
    public void addNewExamplesFrom(DataTable dataTable) {

    }

    @Override
    public void exampleStarted(Map<String, String> map) {

    }

    @Override
    public void exampleFinished() {

    }

    @Override
    public void assumptionViolated(String s) {

    }

    @Override
    public void testRunFinished() {

    }

    @Override
    public void takeScreenshots(List<ScreenshotAndHtmlSource> list) {

    }

    @Override
    public void takeScreenshots(TestResult testResult, List<ScreenshotAndHtmlSource> list) {

    }
}