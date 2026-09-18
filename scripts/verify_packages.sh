#!/bin/bash

echo "=== VÉRIFICATION COHÉRENCE PACKAGES ==="

errors=0

find src/main/java -name "*.java" -type f | while read file; do
  # Extraire le chemin relatif
  rel_path=${file#src/main/java/}
  
  # Extraire le package attendu
  dir_path=$(dirname "$rel_path")
  expected_package=$(echo "$dir_path" | tr '/' '.')
  
  # Extraire le package déclaré
  declared_package=$(grep -m 1 "^package " "$file" | sed 's/package //;s/;//' | tr -d ' ')
  
  # Comparer
  if [ "$declared_package" != "$expected_package" ] && [ -n "$declared_package" ]; then
    echo "❌ ERREUR: $file"
    echo "   Déclaré: $declared_package"
    echo "   Attendu: $expected_package"
    errors=$((errors + 1))
  fi
done

echo "=== FIN VÉRIFICATION ==="
echo "Erreurs trouvées: $errors"
