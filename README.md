# juan_Code
Juan Pablo Ceron personal projects


# CarRegistry

CarRegistry es una aplicación de registro diario de coches que permite realizar operaciones CRUD en una base de datos relacional MySQL. Además, cuenta con un sistema de registro de usuarios que utiliza tokens para acceder y realizar distintas operaciones según el rol asignado.

## Descripción

CarRegistry es una herramienta diseñada para gestionar eficientemente el registro diario de coches en una base de datos. Con esta aplicación, los usuarios pueden realizar las siguientes operaciones:

- **Crear**: Agregar nuevos registros de coches con información detallada, como modelo, marca, año, etc.
- **Leer**: Consultar y visualizar registros existentes en la base de datos.
- **Actualizar**: Modificar información de registros de coches existentes, como actualizar el estado o la información relacionada.
- **Eliminar**: Eliminar registros de coches que ya no sean necesarios.

Además, CarRegistry implementa un sistema de autenticación de usuarios que asigna roles y privilegios basados en tokens. Esto permite un acceso seguro y restringido a las funcionalidades de la aplicación:

- **Usuarios Administradores (Vendor)**: Tienen acceso completo a todas las funcionalidades de la aplicación, incluyendo operaciones CRUD y gestión de usuarios.
- **Usuarios Estándar (Client)**: Tienen acceso limitado y solo pueden realizar operaciones de lectura y algunas operaciones específicas, según la configuración de permisos.
