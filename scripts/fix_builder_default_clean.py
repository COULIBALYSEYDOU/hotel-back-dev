#!/usr/bin/env python3
"""
Script pour nettoyer le formatage après ajout de @Builder.Default
et corriger les fichiers avec des lignes vides en trop.
"""

import re
import os
from pathlib import Path

def clean_builder_default_formatting(file_path):
    """Nettoie le formatage autour de @Builder.Default."""
    try:
        with open(file_path, 'r', encoding='utf-8') as f:
            content = f.read()
        
        original_content = content
        
        # Supprimer les lignes vides multiples après @Builder.Default
        content = re.sub(r'@Builder\.Default\s+\n\s*\n\s*\n', '@Builder.Default\n    ', content)
        content = re.sub(r'@Builder\.Default\s+\n\s*\n', '@Builder.Default\n    ', content)
        
        # S'assurer qu'il n'y a qu'une seule ligne vide après @Builder.Default
        content = re.sub(r'@Builder\.Default\s*\n\s*\n\s+private', '@Builder.Default\n    private', content)
        
        # Nettoyer les lignes vides multiples avant @Builder.Default
        content = re.sub(r'\n\s*\n\s*\n\s*@Builder\.Default', '\n    @Builder.Default', content)
        content = re.sub(r'\n\s*\n\s*@Builder\.Default', '\n    @Builder.Default', content)
        
        if content != original_content:
            with open(file_path, 'w', encoding='utf-8') as f:
                f.write(content)
            return True
        
        return False
    except Exception as e:
        print(f"Erreur lors du nettoyage de {file_path}: {e}")
        return False

def main():
    """Nettoie tous les fichiers Java."""
    base_dir = Path('src/main/java')
    
    if not base_dir.exists():
        print(f"Erreur: {base_dir} n'existe pas")
        return
    
    java_files = list(base_dir.rglob('*.java'))
    modified_count = 0
    
    for java_file in java_files:
        if clean_builder_default_formatting(java_file):
            print(f"✅ Nettoyé: {java_file}")
            modified_count += 1
    
    print(f"\n✅ {modified_count} fichiers nettoyés")

if __name__ == '__main__':
    main()
