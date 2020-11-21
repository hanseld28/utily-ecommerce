package br.com.utily.ecommerce.helper.view;

import br.com.utily.ecommerce.util.constant.EnumUtil;
import br.com.utily.ecommerce.util.constant.folder.EPageFolder;
import br.com.utily.ecommerce.util.constant.view.EView;

import java.util.Collection;
import java.util.stream.Collectors;

public class PathBuilderHelper {

    public static String build(Collection<Enum<?>> paths) {
        Collection<String> pathParts = paths.stream().map(path -> {
            String part = null;
            if (EnumUtil.isPageFolderEnum(path)) {
                EPageFolder pageFolder = EnumUtil.cast(path, EPageFolder.class);
                part = pageFolder.getPath();
            }
            if (EnumUtil.isViewEnum(path)) {
                EView view = EnumUtil.cast(path, EView.class);
                part = view.getPath();
            }
           return part;
        }).collect(Collectors.toList());

        String builded = pathParts.stream().reduce("", String::concat);

        return builded;
    }
}
