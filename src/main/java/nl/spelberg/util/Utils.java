package nl.spelberg.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

/*
 * The contents of this file are subject to the terms
 * of the Common Development and Distribution License
 * (the "License").  You may not use this file except
 * in compliance with the License.
 *
 * You can obtain a copy of the license at
 * glassfish/bootstrap/legal/CDDLv1.0.txt or
 * https://glassfish.dev.java.net/public/CDDLv1.0.html.
 * See the License for the specific language governing
 * permissions and limitations under the License.
 *
 * When distributing Covered Code, include this CDDL
 * HEADER in each file and include the License file at
 * glassfish/bootstrap/legal/CDDLv1.0.txt.  If applicable,
 * add the following below this CDDL HEADER, with the
 * fields enclosed by brackets "[]" replaced with your
 * own identifying information: Portions Copyright [yyyy]
 * [name of copyright owner]
 */

/*
 * @(#)ASCIIUtility.java  1.10 05/08/29
 *
 * Copyright 1997-2005 Sun Microsystems, Inc. All Rights Reserved.
 */

public class Utils {

    public static byte[] getBytes(String path) throws IOException {
        return getBytes(new FileInputStream(path));
    }

    @SuppressWarnings({"UnusedAssignment"})
    public static byte[] getBytes(InputStream is) throws IOException {

        int len;
        int size = 1024;
        byte[] buf;

        if (is instanceof ByteArrayInputStream) {
            size = is.available();
            buf = new byte[size];
            len = is.read(buf, 0, size);
        } else {
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            buf = new byte[size];
            while ((len = is.read(buf, 0, size)) != -1) {
                bos.write(buf, 0, len);
            }
            buf = bos.toByteArray();
        }
        return buf;
    }


    public static SortedSet<String> getFileNames(String directory, String... extensions) {
        File dir = new File(directory);
        Assert.isTrue(dir.isDirectory(), "Geen bestaande directory: '" + directory + "'");

        final List<String> extList = Arrays.asList(extensions);

        File[] files = dir.listFiles(new FileFilter() {
            @Override
            public boolean accept(File file) {
                return file.isFile() && extList.contains(StringUtils.getFilenameExtension(file.getPath()));
            }
        });

        SortedSet<String> sortedFiles = new TreeSet<String>();
        for (File f : files) {
            sortedFiles.add(f.getName());
        }

        return sortedFiles;
    }

    public static String emptyWhenNull(String s) {
        return s == null ? "" : s;
    }

    public static String emptyWhenNullString(Long l) {
        return l == null ? "" : String.valueOf(l);
    }

    public static String nativePath(String path) {
        return StringUtils.replace(path, "/", File.separator);
    }
}