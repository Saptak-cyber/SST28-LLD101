public class Demo09 {
    public static void main(String[] args) {
        System.out.println("=== Evaluation Pipeline ===");
        Submission sub = new Submission("23BCS1007", "public class A{}", "A.java");
        
        // Dependency injection: construct dependencies and inject them
        IPlagiarismChecker plagiarismChecker = new PlagiarismChecker();
        ICodeGrader codeGrader = new CodeGrader();
        IReportWriter reportWriter = new ReportWriter();
        Rubric rubric = new Rubric();
        
        EvaluationPipeline pipeline = new EvaluationPipeline(
            plagiarismChecker, codeGrader, reportWriter, rubric
        );
        pipeline.evaluate(sub);
    }
}
