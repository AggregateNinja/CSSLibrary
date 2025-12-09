
package Utility;


public class ExceptionUtil
{
    
    /**
     * Returns a string of the following:
     *  The throwable's getMessage(), or "[NULL]" if null
     *  The stack trace, space-separated
     * 
     * The getMessage() output is preceded by "ex:" and the stack trace is
     *  preceded by "stack:"
     * @param ex
     * @return 
     */
    public static String getMessage(Throwable ex)
    {
        return getMessage(null, ex);
    }
    
    
    /**
     *  Appends the following to the supplied 'userMessage' argument and returns it:
     *      The throwable's getMessage() (if not NULL, otherwise "[NULL]"
     *      The stack trace
     * 
     * The getMessage() output is preceded by "ex:" and the stack trace is
     *  preceded by "stack:"
     * 
     * @param userMessage
     * @param ex
     * @return 
     */
    public static String getMessage(String userMessage, Throwable ex)
    {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("");
        if (userMessage != null) stringBuilder.append(userMessage);
        
        if (ex != null)
        {
            stringBuilder.append("ex:");
            if (ex.getMessage() == null)
            {
                stringBuilder.append("[NULL]");
            }
            else
            {
                stringBuilder.append(ex.getMessage());
            }
            
            StackTraceElement[] stackTraceElements = ex.getStackTrace();
            if (stackTraceElements != null && stackTraceElements.length > 0)
            {
                stringBuilder.append(" stack:");
                for (StackTraceElement stackTraceElement : stackTraceElements)
                {
                    if (stackTraceElement != null
                            && stackTraceElement.toString() != null
                            && stackTraceElement.toString().isEmpty() == false)
                    {
                        stringBuilder.append(" ");
                        stringBuilder.append(stackTraceElement.toString());
                    }
                }
            }
        }
        return stringBuilder.toString();
    }
}
