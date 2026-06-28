import os

files = [
    'shared/data/build.gradle.kts',
    'shared/database/build.gradle.kts',
    'shared/datastore/build.gradle.kts',
    'shared/device/build.gradle.kts',
    'shared/domain/build.gradle.kts',
    'shared/model/build.gradle.kts',
    'shared/presentation/build.gradle.kts',
    'shared/resources/build.gradle.kts',
    'shared/utils/build.gradle.kts'
]

for file in files:
    with open(file, 'r') as f:
        content = f.read()
    
    content = content.replace('    iosX64()\n', '')
    
    with open(file, 'w') as f:
        f.write(content)

di_file = 'shared/di/build.gradle.kts'
with open(di_file, 'r') as f:
    di_content = f.read()

import re
di_content = re.sub(r'\s*iosX64\s*\{\s*binaries\.framework\s*\{\s*baseName\s*=\s*xcfName\s*\}\s*\}', '', di_content)

with open(di_file, 'w') as f:
    f.write(di_content)
