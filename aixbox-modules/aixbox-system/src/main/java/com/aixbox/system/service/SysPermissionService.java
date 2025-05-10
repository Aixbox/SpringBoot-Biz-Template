package com.aixbox.system.service;


import java.util.Set;

/**
 *
 */
public interface SysPermissionService {
    Set<String> getRolePermission(Long userId);

    Set<String> getMenuPermission(Long userId);
}
