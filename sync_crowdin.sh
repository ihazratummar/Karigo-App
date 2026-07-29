#!/bin/bash

# A helper script to quickly sync with Crowdin manually
# Make sure you have the Crowdin CLI installed: brew install crowdin

# Set these variables or export them in your terminal before running
# export CROWDIN_PROJECT_ID="your_project_id"
# export CROWDIN_PERSONAL_TOKEN="your_personal_token"

# Load environment variables from local.properties if it exists (standard Android way)
if [ -f local.properties ]; then
    # || [ -n "$key" ] ensures we don't skip the last line if it's missing a newline
    while IFS='=' read -r key value || [ -n "$key" ]; do
        # Strip trailing carriage returns (\r) which can break exports
        key=$(echo "$key" | tr -d '\r' | xargs)
        value=$(echo "$value" | tr -d '\r' | xargs)
        
        # Ignore comments, empty lines, and keys with dots (like sdk.dir) which bash can't export
        if [[ ! "$key" =~ ^# ]] && [[ -n "$key" ]] && [[ ! "$key" =~ \. ]]; then
            # Strip quotes from value just in case they were added
            value=$(echo "$value" | sed -e 's/^"//' -e 's/"$//')
            export "$key"="$value"
        fi
    done < local.properties
fi

if [ -z "$CROWDIN_PROJECT_ID" ] || [ -z "$CROWDIN_PERSONAL_TOKEN" ]; then
    echo "Error: CROWDIN_PROJECT_ID and CROWDIN_PERSONAL_TOKEN must be set."
    echo "Please add them to your local.properties file in the root directory:"
    echo "  CROWDIN_PROJECT_ID=your_id"
    echo "  CROWDIN_PERSONAL_TOKEN=your_token"
    exit 1
fi

echo "Uploading latest English source strings to Crowdin..."
crowdin upload sources

echo "Uploading local translations to Crowdin..."
crowdin upload translations

echo "Downloading latest translations from Crowdin..."
crowdin download translations

echo "Done! You can now commit and push the new translations to your branch."
