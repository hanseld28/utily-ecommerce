package br.com.utily.ecommerce.util.constant;

import br.com.utily.ecommerce.util.constant.folder.EPageFolder;
import br.com.utily.ecommerce.util.constant.view.EView;

public class EnumUtil {

    public static <T extends Enum<T>> T cast(final Enum<?> from, final Class<T> to) {
        return to.cast(from);
    }

    public static boolean isPageFolderEnum(Enum target) {
        return target instanceof EPageFolder;
    }

    public static boolean isViewEnum(Enum target) {
        return target instanceof EView;
    }

}
