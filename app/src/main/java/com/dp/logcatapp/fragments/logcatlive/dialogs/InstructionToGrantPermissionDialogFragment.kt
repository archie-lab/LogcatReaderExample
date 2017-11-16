package com.dp.logcatapp.fragments.logcatlive.dialogs

import android.Manifest
import android.app.Dialog
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.os.Bundle
import android.support.v7.app.AlertDialog
import com.dp.logcatapp.R
import com.dp.logcatapp.fragments.base.BaseDialogFragment
import com.dp.logcatapp.util.RootUtils
import com.dp.logcatapp.util.showToast

class InstructionToGrantPermissionDialogFragment : BaseDialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return AlertDialog.Builder(activity)
                .setTitle(R.string.read_logs_permission_required)
                .setMessage(R.string.grant_read_logs_permission_instruction)
                .setPositiveButton(R.string.grant_read_logs_permission_action_using_root, { _, _ ->
                    if (RootUtils.grantReadLogsPermission(activity)) {
                        activity.showToast(getString(R.string.success))
                    } else {
                        activity.showToast(getString(R.string.failed))
                    }
                })
                .setNegativeButton(R.string.copy_to_clipboard, { _, _ ->
                    val cmd = "adb shell pm grant ${activity.packageName} " +
                            Manifest.permission.READ_LOGS
                    val cm = activity.getSystemService(Context.CLIPBOARD_SERVICE)
                            as ClipboardManager
                    cm.primaryClip = ClipData.newPlainText("Adb command",
                            cmd)
                })
                .create()
    }

    companion object {
        val TAG = InstructionToGrantPermissionDialogFragment::class.qualifiedName
    }

}