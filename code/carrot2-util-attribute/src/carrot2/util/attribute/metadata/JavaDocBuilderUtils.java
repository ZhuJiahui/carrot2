/**
 *
 */
package carrot2.util.attribute.metadata;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.thoughtworks.qdox.model.AbstractJavaEntity;
import com.thoughtworks.qdox.model.Annotation;

/**
 *
 */
public final class JavaDocBuilderUtils
{
    private static final Pattern FIRST_SENTENCE_PATTERN = Pattern
        .compile("\\.(?<!((\\w\\.){2,5}+))(\\s|\\z)");

    private static final Pattern LINK_TO_TEXT_PATTERN = Pattern
        .compile("\\{@link\\s(.+)\\}");

    private JavaDocBuilderUtils()
    {
        // No instantiation
    }

    public static boolean hasAnnotation(AbstractJavaEntity javaEntity,
        Class<?> requestedAnnotationClass)
    {
        return getAnnotation(javaEntity, requestedAnnotationClass) != null;
    }

    public static Annotation getAnnotation(AbstractJavaEntity javaEntity,
        Class<?> requestedAnnotationClass)
    {
        for (final Annotation annotation : javaEntity.getAnnotations())
        {
            if (requestedAnnotationClass.getName()
                .equals(annotation.getType().getValue()))
            {
                return annotation;
            }
        }
        return null;
    }

    public static int getEndOfFirstSentenceCharIndex(String text)
    {
        final Matcher matcher = FIRST_SENTENCE_PATTERN.matcher(text);
        if (matcher.find())
        {
            return matcher.start();
        }
        else
        {
            return -1;
        }
    }

    public static String normalizeSpaces(String string)
    {
        if (string == null)
        {
            return null;
        }
        return string.replaceAll("[\\t\\r\\n]+", " ");
    }

    public static String renderInlineTags(String comment)
    {
        return LINK_TO_TEXT_PATTERN.matcher(comment).replaceAll("$1");
    }

    public static String toPlainText(String comment)
    {
        if (comment == null)
        {
            return null;
        }
        final String normalizedSpace = normalizeSpaces(comment);
        final String linksRendered = renderInlineTags(normalizedSpace);
        final String trimmed = linksRendered.trim();
        if (trimmed.length() == 0)
        {
            return null;
        }

        return trimmed;
    }
}
