package quiz.Intelli.app.httpHelpers;

/**
 * Created by Dan on 4/20/2015.
 */
public interface ConnectionCallbacks
{
    public void successQuery(SuccessMessage successMessage);
    public void failedQuery(ErrorMessage errorMessage);
}
