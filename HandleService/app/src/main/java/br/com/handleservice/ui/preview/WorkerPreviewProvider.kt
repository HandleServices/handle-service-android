package br.com.handleservice.ui.preview

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import br.com.handleservice.domain.model.Worker
import br.com.handleservice.ui.mock.getMockWorker

class WorkerPreviewProvider : PreviewParameterProvider<Worker> {
    override val values: Sequence<Worker> = getMockWorker()
}