package org.mkdev.ut.exceptions;

/**
 * @author Michał Kołodziejski &lt;<I><A href="mailto:michal.kolodziejski@gmail.com">michal.kolodziejski@gmail.com</A></I>&gt;
 * @version 1.0
 * @license: GPLv3 (http://www.gnu.org/licenses/gpl-3.0.txt)
 * @since: 2015-02-01
 */
public class InvalidConfigurationException extends RuntimeException {

    private static final long serialVersionUID = -2169053418314223912L;

    public InvalidConfigurationException(Throwable cause) {super(cause);}

    public InvalidConfigurationException(String message) {
        super(message);
    }

    public InvalidConfigurationException(String message, Throwable e) {
        super(message, e);
    }
}
