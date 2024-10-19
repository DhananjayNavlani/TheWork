package com.example.roughwork

import android.accessibilityservice.AccessibilityService
import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log
import android.view.KeyEvent
import android.view.accessibility.AccessibilityEvent
import android.view.accessibility.AccessibilityNodeInfo

class MyAccessibilityService : AccessibilityService() {

    override fun onSystemActionsChanged() {
        super.onSystemActionsChanged()

    }

    override fun onKeyEvent(event: KeyEvent?): Boolean {
        Log.d("KeyEvent", "onKeyEvent: The ${KeyEvent.keyCodeToString(event?.keyCode ?: 0)}")
        return super.onKeyEvent(event)
    }

    override fun onGesture(gestureId: Int): Boolean {
        return super.onGesture(gestureId)
    }

    override fun onAccessibilityEvent(event: AccessibilityEvent?) {
        if (event != null) {
            // Get the root node of the view hierarchy
            val rootNode = rootInActiveWindow

            // Log the type of event
            Log.d("AccessibilityEvent", "Event type: ${AccessibilityEvent.eventTypeToString(event.eventType)}")

            if (rootNode != null) {
                // Recursively log the entire view hierarchy
                logViewHierarchy(rootNode, 0)
            } else {
                Log.d("AccessibilityEvent", "Root node is null, no active window.")
            }
        }
    }

    private fun logViewHierarchy(nodeInfo: AccessibilityNodeInfo, depth: Int) {
        val indent = " ".repeat(depth*2)
        // Log basic attributes of the node
        Log.d("ViewHierarchy", "${indent}Class: ${nodeInfo?.className}")
        Log.d("ViewHierarchy", "${indent}Text: ${nodeInfo?.text}")
        Log.d("ViewHierarchy", "${indent}ContentDescription: ${nodeInfo?.contentDescription}")
        Log.d("ViewHierarchy", "${indent}Clickable: ${nodeInfo?.isClickable}")
        Log.d("ViewHierarchy", "${indent}Enabled: ${nodeInfo?.isEnabled}")
        Log.d("ViewHierarchy", "${indent}Focusable: ${nodeInfo?.isFocusable}")
        Log.d("ViewHierarchy", "${indent}Checked: ${nodeInfo?.isChecked}")
        Log.d("ViewHierarchy", "${indent}Focused: ${nodeInfo?.isFocused}")
        Log.d("ViewHierarchy", "${indent}VisibleToUser: ${nodeInfo?.isVisibleToUser}")

        // Log additional attributes you may need for permission checks
        val resourceId = nodeInfo?.viewIdResourceName
        Log.d("ViewHierarchy", "${indent}Resource ID: $resourceId")

        for(i in 0 until nodeInfo.childCount ){
            val childNode = nodeInfo.getChild(i)
            if(childNode != null){
                logViewHierarchy(childNode,depth + 1)
            }
        }
    }

    override fun onInterrupt() {
    }
}