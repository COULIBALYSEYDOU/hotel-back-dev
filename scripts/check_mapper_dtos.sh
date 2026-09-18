#!/bin/bash

echo "=== VÉRIFICATION DTOs DANS MAPPERS ==="

errors=0

# Trouver tous les mappers
find src/ -name "*Mapper.java" -type f | while read mapper; do
  echo ""
  echo "Analyse: $mapper"
  
  # Extraire les types utilisés dans les méthodes
  grep -E "toEntity|toResponse|toDto|updateEntity" "$mapper" | grep -oE "[A-Z][a-zA-Z0-9]*Request|[A-Z][a-zA-Z0-9]*Response|[A-Z][a-zA-Z0-9]*DTO" | sort -u | while read dto; do
    
    # Chercher si le DTO existe
    dto_file=$(find src/ -name "${dto}.java" 2>/dev/null)
    
    if [ -z "$dto_file" ]; then
      echo "  ❌ DTO MANQUANT: $dto (référencé dans $(basename $mapper))"
      errors=$((errors + 1))
    else
      echo "  ✅ DTO OK: $dto"
    fi
  done
done

echo ""
echo "=== FIN VÉRIFICATION ==="
echo "Erreurs trouvées: $errors"
