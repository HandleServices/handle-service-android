package br.com.handleservice.ui.preview

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import br.com.handleservice.domain.model.Service
import br.com.handleservice.ui.mock.getMockServices

class ServicesPreviewProvider : PreviewParameterProvider<Service> {
    override val values: Sequence<Service> = getMockServices().asSequence()
}