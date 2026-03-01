public class EvaluationPipeline {
    private final IPlagiarismChecker plagiarismChecker;
    private final ICodeGrader codeGrader;
    private final IReportWriter reportWriter;
    private final Rubric rubric;

    public EvaluationPipeline(IPlagiarismChecker plagiarismChecker, 
                              ICodeGrader codeGrader, 
                              IReportWriter reportWriter, 
                              Rubric rubric) {
        this.plagiarismChecker = plagiarismChecker;
        this.codeGrader = codeGrader;
        this.reportWriter = reportWriter;
        this.rubric = rubric;
    }

    public void evaluate(Submission sub) {
        int plag = plagiarismChecker.check(sub);
        System.out.println("PlagiarismScore=" + plag);

        int code = codeGrader.grade(sub, rubric);
        System.out.println("CodeScore=" + code);

        String reportName = reportWriter.write(sub, plag, code);
        System.out.println("Report written: " + reportName);

        int total = plag + code;
        String result = (total >= 90) ? "PASS" : "FAIL";
        System.out.println("FINAL: " + result + " (total=" + total + ")");
    }
}
